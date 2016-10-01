package br.senai.sp.informatica.todolist.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.auth0.jwt.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi.MD5;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	@Column(length = 100, unique = true)
	private String login;

	@Column(length = 50)
	private String senha;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		Md5PasswordEncoder enconder = new Md5PasswordEncoder();
		this.senha = enconder.encodePassword(senha, null);
	}

	

}


