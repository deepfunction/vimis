package ru.akrtkv.egisz.vimis.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import ru.akrtkv.egisz.vimis.service.MisMoService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;

public class MoOidValidator implements ConstraintValidator<MoOid, String> {

    private final MisMoService misMoService;

    private final MessageSource messageSource;

    @Autowired
    public MoOidValidator(MisMoService misMoService, MessageSource messageSource) {
        this.misMoService = misMoService;
        this.messageSource = messageSource;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (!misMoService.getMoOids().contains(value)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    value + " " + messageSource.getMessage("oidNotValidMessage", null, Locale.getDefault())
            ).addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }
}
