package com.daddario.facturacion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FacturaControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void crearYListarFacturas() throws Exception {
        String body = """
                {"clienteId":1,"items":[{"productoId":1,"cantidad":1},{"productoId":2,"cantidad":2}]}
                """;
        mvc.perform(post("/api/facturas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        mvc.perform(get("/api/facturas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }
}
