package ru.akrtkv.egisz.vimis.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.zip.CRC32;

public class SignatureDataValidator implements ConstraintValidator<SignatureData, ru.akrtkv.egisz.vimis.dto.rir.Signature> {

    private final MessageSource messageSource;

    @Autowired
    public SignatureDataValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public boolean isValid(ru.akrtkv.egisz.vimis.dto.rir.Signature signature, ConstraintValidatorContext constraintValidatorContext) {
        if (signature != null && signature.getData() != null) {
            var checksumEngine = new CRC32();
            checksumEngine.update(signature.getData().getBytes(StandardCharsets.UTF_8), 0, signature.getData().getBytes(StandardCharsets.UTF_8).length);
            if (checksumEngine.getValue() != signature.getChecksum()) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(
                        messageSource.getMessage("binaryDataNotValidMessage", null, Locale.getDefault())
                ).addConstraintViolation();
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}
