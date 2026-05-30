package br.com.cwi.redesocial.mapper.curtida;

import br.com.cwi.redesocial.domain.Curtida;
import br.com.cwi.redesocial.factory.CurtidaFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class CurtidaMapperTest {

    @Test
    @DisplayName("deve instanciar mapper")
    void deveInstanciarMapper() {
        assertNotNull(new CurtidaMapper());
    }

    @Test
    @DisplayName("deve mapear post e usuario para entity")
    void deveMapearPostEUsuarioParaEntity() {
        Curtida curtidaBase = CurtidaFactory.getCurtida();

        Curtida curtida = CurtidaMapper.toEntity(curtidaBase.getPost(), curtidaBase.getUsuario());

        assertSame(curtidaBase.getPost(), curtida.getPost());
        assertSame(curtidaBase.getUsuario(), curtida.getUsuario());
    }
}
