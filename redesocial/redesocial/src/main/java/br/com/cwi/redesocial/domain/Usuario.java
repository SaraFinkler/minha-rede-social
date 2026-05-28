package br.com.cwi.redesocial.domain;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nomeCompleto;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(length = 50)
    private String apelido;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false, length = 255)
    private String senha;

    @Column(length = 512)
    private String imagemPerfil;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime dataAlteracao;

    @OneToMany(mappedBy = "usuario")
    private List<Post> posts;

    private boolean ativo;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Permissao> permissoes = new ArrayList<>();

    public void adicionarPermissao(Permissao permissao){
        this.permissoes.add(permissao);
        permissao.setUsuario(this);
    }
}
