package com.adviserit.ms.industrias.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.adviserit.ms.industrias.web.rest.TestUtil;

public class IndustriasTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Industrias.class);
        Industrias industrias1 = new Industrias();
        industrias1.setId(1L);
        Industrias industrias2 = new Industrias();
        industrias2.setId(industrias1.getId());
        assertThat(industrias1).isEqualTo(industrias2);
        industrias2.setId(2L);
        assertThat(industrias1).isNotEqualTo(industrias2);
        industrias1.setId(null);
        assertThat(industrias1).isNotEqualTo(industrias2);
    }
}
