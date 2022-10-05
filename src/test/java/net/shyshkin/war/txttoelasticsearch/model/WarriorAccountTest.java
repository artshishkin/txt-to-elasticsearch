package net.shyshkin.war.txttoelasticsearch.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WarriorAccountTest {

    @Test
    void getLastFirstName_shouldReturnNameWithoutFathersName() {
        //given
        WarriorAccount account = WarriorAccount.builder()
                .fullName("ШИШКІН АРТЕМ ВІКТОРОВИЧ")
                .build();

        //when
        String lastFirstName = account.getLastFirstName();

        //then
        assertThat(lastFirstName).isEqualTo("ШИШКІН АРТЕМ");
    }

    @Test
    void getLastFirstName_withManySpaces_shouldReturnNameWithoutFathersName() {
        //given
        WarriorAccount account = WarriorAccount.builder()
                .fullName("ШИШКІН      АРТЕМ    ВІКТОРОВИЧ")
                .build();

        //when
        String lastFirstName = account.getLastFirstName();

        //then
        assertThat(lastFirstName).isEqualTo("ШИШКІН АРТЕМ");
    }

    @Test
    void getLastFirstName_withoutFathersName_shouldReturnFullnameWithoutModification() {
        //given
        WarriorAccount account = WarriorAccount.builder()
                .fullName("ШИШКІН АРТЕМ")
                .build();

        //when
        String lastFirstName = account.getLastFirstName();

        //then
        assertThat(lastFirstName).isEqualTo("ШИШКІН АРТЕМ");
    }
}