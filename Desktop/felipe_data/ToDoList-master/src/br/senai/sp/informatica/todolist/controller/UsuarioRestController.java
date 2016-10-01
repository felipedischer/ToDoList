package br.senai.sp.informatica.todolist.controller;

import java.net.URI;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWTSigner;

import br.senai.sp.informatica.todolist.dao.UsuarioDao;
import br.senai.sp.informatica.todolist.modelo.Usuario;

@RestController
public class UsuarioRestController {
	
	public static final String SECRET = "todolistfelipe";
	public static final String ISSUER = "http://www.sp.senai.br";

	@Autowired
	private UsuarioDao usuarioDao;

	@RequestMapping(value = "/usuario", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Usuario> inserir(@RequestBody Usuario usuario) {
		try {

			usuarioDao.inserir(usuario);
			URI location = new URI("/usuario/" + usuario.getId());

			return ResponseEntity.created(location).body(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/usuario/{idUsuario}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Usuario> obterUsuario(@PathVariable Long idUsuario) {
		Usuario usuario = usuarioDao.obter(idUsuario);
		if (usuario != null) {
			return ResponseEntity.ok().body(usuario);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> logar(@RequestBody Usuario usuario) {

		try {
			usuario = usuarioDao.logar(usuario);
			if(usuario != null){
				//data e  emissão do token
				
				long iat = System.currentTimeMillis() / 1000;
				//data de expiração do token
				
				long exp = iat + 60;
				
				//objeto que irá gerar o token
				
				JWTSigner signer = new JWTSigner(SECRET); 
				HashMap<String, Object> claims = new HashMap<>();
				claims.put("iat", iat);
				claims.put("exp", exp);
				claims.put("iss", ISSUER);
				claims.put("id_usuario", usuario.getId());
				//gerar o token
				String jwt = signer.sign(claims);
				JSONObject token = new JSONObject();
				token.put("token", jwt);
				return ResponseEntity.ok(token.toString());
			}else{
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
