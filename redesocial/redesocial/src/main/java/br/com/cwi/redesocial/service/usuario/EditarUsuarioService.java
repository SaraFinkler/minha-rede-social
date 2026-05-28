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
            UsuarioResponse usuarioLogado = usuarioAutenticadoService.get();

            Usuario usuario = usuarioRepository.findById(usuarioLogado.getId())
                    .orElseThrow(() ->
                            new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "Usuário não encontrado"
                            )
                    );

            if (preenchido(request.getNomeCompleto())) {
                usuario.setNomeCompleto(request.getNomeCompleto());
            }

            if (preenchido(request.getApelido())) {
                usuario.setApelido(request.getApelido());
            }

            if (preenchido(request.getImagemPerfil())) {
                usuario.setImagemPerfil(request.getImagemPerfil());
            }

            usuarioRepository.save(usuario);

            return UsuarioMapper.toResponse(usuario);
        }

    private boolean preenchido(String valor) {
        return valor != null && !valor.isBlank();
    }
}