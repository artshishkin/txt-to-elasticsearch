package net.shyshkin.war.txttoelasticsearch.util;

import net.shyshkin.war.txttoelasticsearch.model.SearchNameType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SearchUtilTest {

    @Nested
    class GetFullNameTests{

        @Test
        void getFullName_shouldReturnNameWithoutFathersName() {
            //given
            String totalQuery = "ШИШКІН АРТЕМ ВІКТОРОВИЧ";

            //when
            String queryName = SearchUtil.getSearchName(totalQuery, SearchNameType.FULL_NAME);

            //then
            assertThat(queryName).isEqualTo("ШИШКІН АРТЕМ");
        }

        @Test
        void getFullName_withManySpaces_shouldReturnNameWithoutFathersName() {
            //given
            String totalQuery = "ШИШКІН      АРТЕМ    ВІКТОРОВИЧ";

            //when
            String queryName = SearchUtil.getSearchName(totalQuery, SearchNameType.FULL_NAME);

            //then
            assertThat(queryName).isEqualTo("ШИШКІН АРТЕМ");
        }

        @Test
        void getFullName_withoutFathersName_shouldReturnFullnameWithoutModification() {
            //given
            String totalQuery = "ШИШКІН АРТЕМ";

            //when
            String queryName = SearchUtil.getSearchName(totalQuery, SearchNameType.FULL_NAME);

            //then
            assertThat(queryName).isEqualTo("ШИШКІН АРТЕМ");
        }
    }

    @Nested
    class GetFirstNameTests {

        @Test
        void getFirstName_shouldReturnFirstName() {
            //given
            String totalQuery = "ШИШКІН АРТЕМ ВІКТОРОВИЧ";

            //when
            String queryName = SearchUtil.getSearchName(totalQuery, SearchNameType.FIRST_NAME);

            //then
            assertThat(queryName).isEqualTo("АРТЕМ");
        }

        @Test
        void getFirstName_withManySpaces_shouldReturnFirstName() {
            //given
            String totalQuery = "ШИШКІН      АРТЕМ    ВІКТОРОВИЧ";

            //when
            String queryName = SearchUtil.getSearchName(totalQuery, SearchNameType.FIRST_NAME);

            //then
            assertThat(queryName).isEqualTo("АРТЕМ");
        }
    }


    @Nested
    class GetLastNameTests {

        @Test
        void getLastName_shouldReturnLastName() {
            //given
            String totalQuery = "ШИШКІН АРТЕМ ВІКТОРОВИЧ";

            //when
            String queryName = SearchUtil.getSearchName(totalQuery, SearchNameType.LAST_NAME);

            //then
            assertThat(queryName).isEqualTo("ШИШКІН");
        }

        @Test
        void getLastName_withManySpaces_shouldReturnLastName() {
            //given
            String totalQuery = "ШИШКІН      АРТЕМ    ВІКТОРОВИЧ";

            //when
            String queryName = SearchUtil.getSearchName(totalQuery, SearchNameType.LAST_NAME);

            //then
            assertThat(queryName).isEqualTo("ШИШКІН");
        }
    }

    @Nested
    class GetTotalQueryTests {

        @Test
        void getTotalQuery_shouldReturnTotalQueryWithoutModification() {
            //given
            String totalQuery = "ШИШКІН АРТЕМ ВІКТОРОВИЧ";

            //when
            String queryName = SearchUtil.getSearchName(totalQuery, SearchNameType.TOTAL_QUERY);

            //then
            assertThat(queryName).isEqualTo("ШИШКІН АРТЕМ ВІКТОРОВИЧ");
        }

        @Test
        void getTotalQuery_withManySpaces_shouldReturnTotalQueryWithoutModification() {
            //given
            String totalQuery = "ШИШКІН      АРТЕМ    ВІКТОРОВИЧ";

            //when
            String queryName = SearchUtil.getSearchName(totalQuery, SearchNameType.TOTAL_QUERY);

            //then
            assertThat(queryName).isEqualTo("ШИШКІН      АРТЕМ    ВІКТОРОВИЧ");
        }
    }
}