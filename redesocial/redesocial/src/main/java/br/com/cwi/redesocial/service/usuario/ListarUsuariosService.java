package br.com.cwi.redesocial.service.usuario;

import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.mapper.usuario.UsuarioMapper;
import br.com.cwi.redesocial.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarUsuariosService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioAutenticadoService usuarioAutenticadoService;

	public Page<UsuarioResponse> listar(String busca, Pageable pageable) {
		Long usuarioAutenticadoId = usuarioAutenticadoService.get().getId();
		return usuarioRepository.buscarPorNomeOuEmail(busca, usuarioAutenticadoId, pageable)
				.map(UsuarioMapper::toResponse);
	}
}
