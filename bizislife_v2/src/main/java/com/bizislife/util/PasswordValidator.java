package com.bizislife.util;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.RuleResult;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;
import org.passay.WhitespaceRule;

import com.bizislife.util.annotation.ValidPassword;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

	@Override
	public void initialize(ValidPassword constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		org.passay.PasswordValidator validator = new org.passay.PasswordValidator(
				Arrays.asList(
						new LengthRule(8, 20),
						new WhitespaceRule()
						));
		
		RuleResult ruleResult = validator.validate(new PasswordData(value));
		if (ruleResult.isValid()) {
			return true;
		}
        
		// create message string from message list:
		StringBuilder messages = new StringBuilder();
		if (validator.getMessages(ruleResult)!=null) {
			for (String msg : validator.getMessages(ruleResult)) {
				if (StringUtils.isNotBlank(msg)) {
					messages.append(msg).append(" ");
				}
			}
		}
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(messages.toString()).addConstraintViolation();
		return false;
	}

}
