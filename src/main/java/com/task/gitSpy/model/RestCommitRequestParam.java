package com.task.gitSpy.model;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true, includeFieldNames = true)
public class RestCommitRequestParam {

	@Getter
	private int year;
	@Getter
	private String country;
	@Getter
	private String gitHubUser;
	@Getter
	private int status = 200;
	@Getter
	private String error;

	public RestCommitRequestParam(Map<String, String> params) {
		String year;
		try {
			year = GetParam(params, APIParameter.YEAR);
			this.year = Integer.parseInt(year);
			country = GetParam(params, APIParameter.COUNTRY);
			gitHubUser = GetParam(params, APIParameter.GITHUBUSER);
		} catch (FieldDontExistException e1) {
			error = e1.getMessage();
			status= HttpStatus.BAD_REQUEST.value();
		}catch (NumberFormatException e) {
			error = "Year value is not a number. Must have format YYYY";
			status= HttpStatus.BAD_REQUEST.value();
			return;
		}

	}

	public String GetParam(Map<String, String> params, APIParameter aPIParameter) throws FieldDontExistException {
		if (params.containsKey(aPIParameter.toString())) {
			return params.get(aPIParameter.toString());
		} else {
			throw new FieldDontExistException(aPIParameter.toString());
		}
	}

	public enum APIParameter {
		YEAR("year"), COUNTRY("country"), GITHUBUSER("gitHubUser");

		private String param;

		APIParameter(String param) {
			this.param = param;
		}

		@Override
		public String toString() {
			return param;
		}
	}

	class FieldDontExistException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public FieldDontExistException(String fieldName) {
			super("Don't found Field:" + fieldName);
		}
	}
}
