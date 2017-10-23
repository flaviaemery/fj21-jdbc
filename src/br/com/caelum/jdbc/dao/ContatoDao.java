package br.com.caelum.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.caelum.jdbc.ConnectionFactory;
import br.com.caelum.jdbc.daoException.DAOException;
import br.com.caelum.jdbc.modelo.Contato;

public class ContatoDao {
	private Connection connection;
	
	public ContatoDao(Connection connection) {
		this.connection = connection;
	}
	
	public ContatoDao() {
		this.setConnection(new ConnectionFactory().getConnection());
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

public void adiciona (Contato contato) {
	String sql = "insert into contatos" + "(nome,email,endereco,dataNascimento)"
							 
				 + "values (?,?,?,?)";
	
	try {
		//prepared para inserção
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		//seta os valores
		stmt.setString(1, contato.getNome());
		stmt.setString(2, contato.getEmail());
		stmt.setString(3,contato.getEndereco());
//		SimpleDateFormat formatoDaData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//		Calendar tempo = contato.getDataNascimento();
//		long tempoEmMilissegundos = tempo.getTimeInMillis();
//		stmt.setString(4, formatoDaData.format(tempoEmMilissegundos));
		stmt.setString(4, contato.getDataDeNascimentoEmString());
		
		//executa
		stmt.execute();
		stmt.close();
	} catch (SQLException e) {
		throw new DAOException(e, DAOException._FAIL_TO_INSERT, "Erro ao inserir contato");
	}
}

public List<Contato> getLista() {
	try {
		List<Contato> contatos = new ArrayList<Contato>();
		PreparedStatement stmt = this.connection.prepareStatement("select * from contatos");
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			//criando o objeto Contato
			Contato contato = new Contato();
			contato.setId(rs.getLong("id"));
			contato.setNome(rs.getString("nome"));
			
			contato.setEmail(rs.getString("email"));
			
			contato.setEndereco(rs.getString("endereco"));
			
			//montando a data através do Calendar
			Calendar data = Calendar.getInstance();
			
			contato.setDataDeNascimentoEmString(rs.getString("dataNascimento"));
			
			//adicionando o objeto à Lista
			contatos.add(contato);
		}
		rs.close();
		stmt.close();
		return contatos;
	} catch (SQLException e) {
		throw new DAOException(e, DAOException._ERROR_GETTING_LIST, "Erro ao recuperar contatos do banco de dados");
	}
}

/**
 * Retorna um contato salvo no banco de dados com o ID procurado.
 * @param id Campo id da tabela contatos no banco de dados
 * @return Um objeto {@link Contato}, com as informações do contato no banco de dados.
 */
public Contato pesquisa(Integer id) {
	try {
		//Cria a consulta SQL
		PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM contatos WHERE id = ?");
		//Seta o id procurado na consulta SQL
		stmt.setInt(1, id);
		
		//Executa a consulta
		ResultSet rs = stmt.executeQuery();
		
		//Posiciona o cursor de leitura no primeiro resultado
		rs.next();
		
		//Cria um objeto do tipo Contato vazio
		Contato contato = new Contato();
		
		//Preenche o objeto com as informações recuperadas do banco de dados
		contato.setId(rs.getLong("id"));
		contato.setNome(rs.getString("nome"));
		
		contato.setEmail(rs.getString("email"));
		
		contato.setEndereco(rs.getString("endereco"));
		
		contato.setDataDeNascimentoEmString(rs.getString("dataNascimento"));
		
		rs.close();
		stmt.close();
		return contato;
	} catch (SQLException e) {
		throw new DAOException(e, DAOException._ERRO_NA_PESQUISA, "Erro ao pesquisar o contato com o id=" + id + " no banco de dados.");
	}
}

public void altera(Contato contato) {
	String sql = "update contatos set nome=?, email=?"+
				"endereco=?, dataNascimento=? where id=?";
			try {
				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.setString(1, contato.getNome());
				stmt.setString(2, contato.getEmail());
				stmt.setString(3, contato.getEndereco());
				stmt.setDate(4, new Date(contato.getDataNascimento().getTimeInMillis()));
				stmt.setLong(5,contato.getId());
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
}

public void remove(Contato contato) {
	try {
		PreparedStatement stmt = connection.prepareStatement("delete from contatos where id=?");
		stmt.setLong(1, contato.getId());
		stmt.execute();
		stmt.close();
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
}
}
