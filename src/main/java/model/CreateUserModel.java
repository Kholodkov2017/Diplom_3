package model;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.javatuples.Triplet;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class CreateUserModel extends LoginUserModel {
    private final String name;

    public static CreateUserModel createFakeUser(String excludedField) {
        Faker faker = new Faker();
        CreateUserModelBuilder<?, ?> builder = CreateUserModel.builder();
        if (!excludedField.equals("email")) {
            builder.email(faker.internet().emailAddress());
        }

        if (!excludedField.equals("username")) {
            builder.name(faker.name().username());
        }

        if (!excludedField.equals("password")) {
            builder.password(faker.internet().password(6, 16));
        }

        return builder.build();
    }

    public static CreateUserModel createFakeUser(Triplet<String, String, String> fields) {
        Faker faker = new Faker();
        CreateUserModelBuilder<?, ?> builder = CreateUserModel.builder();
        if (fields.getValue0() == null) {
            builder.email(faker.internet().emailAddress());
        } else {
            builder.email(fields.getValue0());
        }

        if (fields.getValue1() == null) {
            builder.name(faker.name().username());
        } else {
            builder.name(fields.getValue1());
        }

        if (fields.getValue2() == null) {
            builder.password(faker.internet().password(6, 16));
        } else {
            builder.password(fields.getValue2());
        }

        return builder.build();
    }
}
