package com.wallet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.wallet.entity.Wallet;
import com.wallet.entity.WalletItem;
import com.wallet.util.enums.TypeEnum;

@SpringBootTest
@ActiveProfiles("test")
public class WalletItemRepositoryTest {

	// Data atual sem horas
	private static final Date DATE = Date
			.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	private static final TypeEnum TYPE = TypeEnum.EN;
	private static final String DESCRIPTION = "Conta de Luz";
	private static final BigDecimal VALUE = BigDecimal.valueOf(65);
	private Long savedWalletItemId = null;
	private Long savedWalletId = null;

	@Autowired
	WalletItemRepository repository;

	@Autowired
	WalletRepository walletRepository;

	Wallet wallet = new Wallet();

	@BeforeEach
	public void setup() {
		wallet.setName("Carteira de Teste");
		wallet.setValue(BigDecimal.valueOf(500));
		walletRepository.save(wallet);

		WalletItem wi = new WalletItem(null, wallet, DATE, TYPE, DESCRIPTION, VALUE);
		repository.save(wi);

		savedWalletItemId = wi.getId();
		savedWalletId = wallet.getId();
	}

	@AfterEach
	public void tearDown() {
		repository.deleteAll();
		walletRepository.deleteAll();
	}

	@Test
	public void testSave() {
		WalletItem wi = new WalletItem(null, wallet, DATE, TYPE, DESCRIPTION, VALUE);
		WalletItem response = repository.save(wi);

		assertNotNull(response);
		assertEquals(DESCRIPTION, response.getDescription());
		assertEquals(DATE, response.getDate());
		assertEquals(TYPE, response.getType());
		assertEquals(VALUE, response.getValue());
	}

	@Test
	public void testSaveInvalid() {
		WalletItem wi = new WalletItem(null, null, DATE, null, DESCRIPTION, null);
		// Captura a EXCEPTION
		assertThrows(ConstraintViolationException.class, () -> {
			repository.save(wi);
		});
	}

	@Test
	public void testUpate() {

		WalletItem walletItem = repository.findById(savedWalletItemId).get();
		String descricao = "Descrição alterada";
		walletItem.setDescription(descricao);

		repository.save(walletItem);

		Optional<WalletItem> response = repository.findById(savedWalletItemId);

		assertEquals(descricao, response.get().getDescription());
	}

	@Test
	public void testDelete() {
		Optional<Wallet> wallet = walletRepository.findById(savedWalletId);
		WalletItem walletItem = new WalletItem(null, wallet.get(), DATE, TYPE, DESCRIPTION, VALUE);
		repository.save(walletItem);

		repository.deleteById(walletItem.getId());
		Optional<WalletItem> response = repository.findById(walletItem.getId());

		assertFalse(response.isPresent());
	}

	@Test
	public void testFindBwtweenDate() {
		Optional<Wallet> w = walletRepository.findById(savedWalletId);

		// Transforma o date em LocalDateTime
		LocalDateTime localDateTime = DATE.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		// Adiciona 5 dias e retransforma em DATE
		Date currentDatePlusFiveDays = Date.from(localDateTime.plusDays(5).atZone(ZoneId.systemDefault()).toInstant());
		// Adiciona 7 dias e retransforma em DATE
		Date currentDatePlusSevenDays = Date.from(localDateTime.plusDays(7).atZone(ZoneId.systemDefault()).toInstant());

		// Salva os dois itens
		repository.save(new WalletItem(null, w.get(), currentDatePlusFiveDays, TYPE, DESCRIPTION, VALUE));
		repository.save(new WalletItem(null, w.get(), currentDatePlusSevenDays, TYPE, DESCRIPTION, VALUE));

		// Instancia o PageRequest na pagina 0 e com 10 itens na pagina
		PageRequest pg = PageRequest.of(0, 10);

		// Efetua a busca pelo WalletId e data maior igual a data atual e menor igual a
		// data passada e passa o pageable
		// ignora o item de currentDatePlusSevenDays
		Page<WalletItem> response = repository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(
				savedWalletId, DATE, currentDatePlusFiveDays, pg);

		// Verifica se o contet é igual a 2
		assertEquals(2, response.getContent().size());
		// verifica se o total de elementos é igual a dois
		assertEquals(2, response.getTotalElements());
		// verifica o id do primeiro item
		assertEquals(savedWalletId, response.getContent().get(0).getWallet().getId());

	}

	@Test
	public void testFindByType() {
		// testando pelo tipo
		List<WalletItem> response = repository.findByWalletIdAndType(savedWalletId, TYPE);

		assertEquals(1, response.size());
		assertEquals(TYPE, response.get(0).getType());
	}

	@Test
	public void testFindByTypeSd() {
		Optional<Wallet> w = walletRepository.findById(savedWalletId);
//		Inserindo um tipo SD
		repository.save(new WalletItem(null, w.get(), DATE, TypeEnum.SD, DESCRIPTION, VALUE));
//		testando pelo tipo
		List<WalletItem> response = repository.findByWalletIdAndType(savedWalletId, TypeEnum.SD);

		assertEquals(1, response.size());
		assertEquals(TypeEnum.SD, response.get(0).getType());
	}
	
	@Test
	public void testSumByWallet() {
		Optional<Wallet> w = walletRepository.findById(savedWalletId);
		
		repository.save(new WalletItem(null, w.get(), DATE, TYPE, DESCRIPTION, BigDecimal.valueOf(150.80)));
		
		BigDecimal response = repository.sumByWalletId(savedWalletId);
		
		//Se o response.compareTo for igual, irá devolver 0
		assertEquals(0, response.compareTo(BigDecimal.valueOf(215.8)));
	}

}
