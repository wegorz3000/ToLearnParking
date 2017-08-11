package com.mobica.rnd.parking.parkingbe.model;

import com.mobica.rnd.parking.parkingbe.ParkingBeApplication;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ParkingBeApplication.class})
@TestPropertySource("classpath:ValidationMessages.properties")
public class UserValidationTests {
    private User user = new User(RandomStringUtils.randomAlphanumeric(10),
            RandomStringUtils.randomAlphanumeric(10) + " " + RandomStringUtils.randomAlphanumeric(10),
            RandomStringUtils.randomAlphanumeric(4),
            RandomStringUtils.randomAlphanumeric(10) + "@mobica.com");

    private Validator validator;

    @Value("${msg.validation.user.usernameandsurname.pattern}")
    private String userNameAndSurnamePatternErrorMessage;
    @Value("${msg.validation.user.usernameandsurname.size}")
    private String userNameAndSurnameSizeErrorMessage;
    @Value("${msg.validation.user.forlettershortcut.size}")
    private String userFourLetterMobicaShortcutSizeErrorMessage;
    @Value("${msg.validation.user.useremailadrdress.pattern}")
    private String userEmailPatternErrorMessage;
    @Value("${msg.validation.user.useremailadrdress.size}")
    private String userEmailSizeErrorMessage;
    @Value("${msg.validation.user.useremailadrdress.notnull}")
    private String userEmailPNotNullErrorMessage;


    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();

    }

    @Test
    public void userNameAndSurnameCorrectDataTest() {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void userNameAndSurnameEmptyFieldTest() {
        user.setUserNameAndSurname(org.apache.commons.lang3.StringUtils.EMPTY);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertEquals(userNameAndSurnamePatternErrorMessage, violations.iterator().next().getMessage());
        user.setUserNameAndSurname(RandomStringUtils.randomAlphanumeric(10) + " " + RandomStringUtils.randomAlphanumeric(10));
    }

    @Test
    public void userNameAndSurnameWrongPatternTest() {
        user.setUserNameAndSurname(RandomStringUtils.randomAlphanumeric(10));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertEquals(userNameAndSurnamePatternErrorMessage, violations.iterator().next().getMessage());
        user.setUserNameAndSurname(RandomStringUtils.randomAlphanumeric(10) + " " + RandomStringUtils.randomAlphanumeric(10));
    }

    @Test
    public void userNameAndSurnameTooMuchCharactersTest() {
        user.setUserNameAndSurname(RandomStringUtils.randomAlphanumeric(260)+ " " + RandomStringUtils.randomAlphanumeric(10));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertEquals(userNameAndSurnameSizeErrorMessage, violations.iterator().next().getMessage());
        user.setUserNameAndSurname(RandomStringUtils.randomAlphanumeric(10) + " " + RandomStringUtils.randomAlphanumeric(10));
    }

    @Test
    public void userEmailCorrectDataTest() {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertTrue(violations.isEmpty());
    }

        @Test
        public void userEmailWrongPatternTest() {
            user.setUserEmailAddress(RandomStringUtils.randomAlphanumeric(10) + "@" + RandomStringUtils.randomAlphanumeric(10) + "@mobica.com" + ".com");
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            Assert.assertEquals(userEmailPatternErrorMessage, violations.iterator().next().getMessage());
            user.setUserEmailAddress(RandomStringUtils.randomAlphanumeric(10) + "@mobica.com");
        }
    @Test
    public void userEmailTooMuchCharactersTest() {
        user.setUserEmailAddress(RandomStringUtils.randomAlphanumeric(110) + "@mobica.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertEquals(userEmailSizeErrorMessage, violations.iterator().next().getMessage());
        user.setUserEmailAddress(RandomStringUtils.randomAlphanumeric(10) + "@mobica.com");
    }

    @Test
    public void fourLetterMobicaShortcutCorrectDataTest() {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertTrue(violations.isEmpty());
    }
    @Test
    public void fourLetterMobicaShortcutEmptyFieldTest() {
        user.setFourLetterMobicaShortcut(org.apache.commons.lang3.StringUtils.EMPTY);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertEquals(userFourLetterMobicaShortcutSizeErrorMessage, violations.iterator().next().getMessage());
        user.setFourLetterMobicaShortcut(  RandomStringUtils.randomAlphanumeric(4));
    }
    @Test
    public void fourLetterMobicaShortcutMoreThanFourCharactersTest() {
        user.setFourLetterMobicaShortcut(  RandomStringUtils.randomAlphanumeric(5));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertEquals(userFourLetterMobicaShortcutSizeErrorMessage, violations.iterator().next().getMessage());
        user.setFourLetterMobicaShortcut(  RandomStringUtils.randomAlphanumeric(4));
    }
    @Test
    public void fourLetterMobicaShortcutLessThanFourCharactersTest() {
        user.setFourLetterMobicaShortcut(       RandomStringUtils.randomAlphanumeric(3));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertEquals(userFourLetterMobicaShortcutSizeErrorMessage, violations.iterator().next().getMessage());
        user.setFourLetterMobicaShortcut(  RandomStringUtils.randomAlphanumeric(4));
    }


}
