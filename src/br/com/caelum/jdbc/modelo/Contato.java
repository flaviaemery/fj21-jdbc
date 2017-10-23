package br.com.caelum.jdbc.modelo;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Contato {
	private Long id;
	private String nome;
	private String email;
	private String endereco;
	private Calendar dataNascimento;
	private String dataDeNascimentoEmString;

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public Calendar getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
		SimpleDateFormat formatoDaData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		this.setDataDeNascimentoEmString(formatoDaData.format(dataNascimento.getTime())); //TODO
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDataDeNascimentoEmString() {
		return dataDeNascimentoEmString;
	}
	public void setDataDeNascimentoEmString(String dataDeNascimentoEmString) {
		this.dataDeNascimentoEmString = dataDeNascimentoEmString;
	}
	
	@Override
	public String toString() {
		return "Contato [id=" + id + ", nome=" + nome + ", email=" + email + ", endereco=" + endereco
				+ ", dataNascimento=" + dataNascimento + ", dataDeNascimentoEmString=" + dataDeNascimentoEmString + "]";
	}

}
