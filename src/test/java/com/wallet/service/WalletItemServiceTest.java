package com.wallet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wallet.entity.Wallet;
import com.wallet.entity.WalletItem;
import com.wallet.repository.WalletItemRepository;
import com.wallet.util.enums.TypeEnum;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class WalletItemServiceTest {

	@MockBean
	WalletItemRepository repository;

	@Autowired
	WalletItemService service;

	// Data atual sem horas
	private static final Date DATE = Date
			.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	private static final TypeEnum TYPE = TypeEnum.EN;
	private static final String DESCRIPTION = "Conta de Luz";
	private static final BigDecimal VALUE = BigDecimal.valueOf(65);

	@Test
	public void testSave() {
		BDDMockito.given(repository.save(Mockito.any(WalletItem.class))).willReturn(getMockWalletItem());

		WalletItem response = service.save(new WalletItem());

		assertNotNull(response);
		assertEquals(DESCRIPTION, response.getDescription());
		assertEquals(0, response.getValue().compareTo(VALUE));
	}

	@Test
	public void testFindBetweenDates() {
		List<WalletItem> list = new ArrayList<>();
		list.add(getMockWalletItem());
		Page<WalletItem> page = new PageImpl<>(list);

		PageRequest pg = PageRequest.of(0, 10);

		BDDMockito
				.given(repository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(Mockito.anyLong(),
						Mockito.any(Date.class), Mockito.any(Date.class), Mockito.any(PageRequest.class)))
				.willReturn(page);

		Page<WalletItem> response = service.findBetweenDates(1l, new Date(), new Date(), pg);

		assertNotNull(response);
		assertEquals(1, response.getContent().size());
		assertEquals(DESCRIPTION, response.getContent().get(0).getDescription());
	}

	@Test
	public void testFindById() {
		BDDMockito.given(repository.findById(Mockito.anyLong())).willReturn(Optional.of(getMockWalletItem()));

		Optional<WalletItem> response = service.findById(1L);

		assertNotNull(response.get());
		assertEquals(DESCRIPTION, response.get().getDescription());
		assertEquals(0, response.get().getValue().compareTo(VALUE));
	}

	@Test
	public void testUpdate() {

		BDDMockito.given(repository.findById(Mockito.anyLong())).willReturn(Optional.of(getMockWalletItem()));
		Optional<WalletItem> response = service.findById(1L);
		response.get().setDescription("Alterado descrição");

		BDDMockito.given(repository.save(Mockito.any(WalletItem.class))).willReturn(response.get());
		WalletItem response2 = service.update(response.get());

		assertNotNull(response2);
		assertEquals("Alterado descrição", response2.getDescription());
		assertEquals(0, response2.getValue().compareTo(VALUE));

	}

	@Test
	public void testDelete() {

		WalletItem response = getMockWalletItem();

		BDDMockito.given(repository.findById(Mockito.anyLong())).willReturn(Optional.of(response));

		service.delete(response);

		Mockito.verify(repository, Mockito.times(1)).delete(response);
	}

	@Test
	public void testFindByType() {
		List<WalletItem> list = new ArrayList<>();
		list.add(getMockWalletItem());

		BDDMockito.given(repository.findByWalletIdAndType(Mockito.anyLong(), Mockito.any(TypeEnum.class)))
				.willReturn(list);

		List<WalletItem> response = service.findByWalletIdAndType(1L, TypeEnum.EN);

		assertNotNull(response);
		assertEquals(TYPE, response.get(0).getType());
	}

	@Test
	public void testSumByWallet() {
		BigDecimal value = BigDecimal.valueOf(45);
		BDDMockito.given(repository.sumByWalletId(Mockito.anyLong())).willReturn(value);

		BigDecimal response = service.sumByWalletId(1L);

		assertEquals(0, response.compareTo(value));
	}

	private WalletItem getMockWalletItem() {
		Wallet w = new Wallet();
		w.setId(1L);

		WalletItem wi = new WalletItem(1L, w, DATE, TYPE, DESCRIPTION, VALUE);
		return wi;
	}

}
