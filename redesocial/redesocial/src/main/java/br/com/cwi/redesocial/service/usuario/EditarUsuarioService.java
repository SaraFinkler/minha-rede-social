package br.com.cwi.redesocial.service.usuario;

import br.com.cwi.redesocial.controller.request.usuario.EditarUsarioRequest;
import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.mapper.usuario.UsuarioMapper;
import br.com.cwi.redesocial.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EditarUsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

        public UsuarioResponse editar(EditarUsarioRequest request) {
            Usuario usuarioLogado = usuarioAutenticadoService.get();

            if (preenchido(request.getNomeCompleto())) {
                usuarioLogado.setNomeCompleto(request.getNomeCompleto());
            }

            if (preenchido(request.getApelido())) {
                usuarioLogado.setApelido(request.getApelido());
            }

            if (preenchido(request.getImagemPerfil())) {
                usuarioLogado.setImagemPerfil(request.getImagemPerfil());
            }

            usuarioRepository.save(usuarioLogado);

            return UsuarioMapper.toResponse(usuarioLogado);
        }

    private boolean preenchido(String valor) {
        return valor != null && !valor.isBlank();
    }
}