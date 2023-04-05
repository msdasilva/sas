package com.ams.sas.resources;

import com.ams.sas.DemoApplicationMockTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PessoaResourceTest extends DemoApplicationMockTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deve_procurar_pessoa_pelo_ddd_e_numero_do_telefone() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/pessoas/{ddd}/{numero}", 86, 35006330)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
