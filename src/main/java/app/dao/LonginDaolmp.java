package app.dao;

import app.Config.MYSQLConnection;
import app.dtos.PersonDto;
import app.dtos.SessionDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class LonginDaolmp implements LoginDao {
    
    public Connection connection = MYSQLConnection.getConnection();

	@Override
	public SessionDto login(PersonDto personDto) throws Exception {
		String query = "INSERT INTO SESION(ROLE,USERNAME) VALUES (?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		int i = 1;
		preparedStatement.setString(i++, personDto.getRol());
		preparedStatement.setString(i++, personDto.getUserName());
		preparedStatement.execute();
		query = "SELECT ID,ROLE,USERNAME FROM SESION WHERE USERNAME = ?";
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, personDto.getUserName());
		ResultSet resulSet = preparedStatement.executeQuery();
		if (resulSet.next()) {
			long id = resulSet.getLong("ID");
			String userRol = resulSet.getString("ROLE");
			String userName = resulSet.getString("USERNAME");
			resulSet.close();
			preparedStatement.close();
			return new SessionDto(id, userName, userRol);
		}
		resulSet.close();
		preparedStatement.close();
		return null;
	}

	@Override
	public void logout(long sessionId) throws Exception {
		String query = "DELETE FROM SESION WHERE ID = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setLong(1, sessionId);
		preparedStatement.execute();
		preparedStatement.close();
	}

	@Override
	public SessionDto findSessionById(long sessionId)throws Exception {
		String query = "SELECT ID,USERROLL,USERNAME FROM SESION WHERE ID = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setLong(1, sessionId);
		ResultSet resulSet = preparedStatement.executeQuery();
		if (resulSet.next()) {
			long id = resulSet.getLong("ID");
			String userRol = resulSet.getString("ROLE");
			String userName = resulSet.getString("USERNAME");
			resulSet.close();
			preparedStatement.close();
			return new SessionDto(id, userName, userRol);
		}
		resulSet.close();
		preparedStatement.close();
		return null;
	}
    
    
}
