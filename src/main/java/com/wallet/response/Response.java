package com.wallet.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Classe generica para ser utilizada
@Getter
@Setter
@NoArgsConstructor
public class Response<T> {
	
	private T data;
	private List<String> errors;
	

}
