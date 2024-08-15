package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.Company;
import model.ModelException;
import model.Contract;

public class MySQLContractDAO implements ContractDAO {

	@Override
	public boolean save(Contract contract) throws ModelException {

		DBHandler db = new DBHandler();

		String sqlInsert = "INSERT INTO contract VALUES (DEFAULT, ?, ? ,? ,?, ?)";

		db.prepareStatement(sqlInsert);

		db.setString(1, contract.getName());
		db.setString(2, contract.getDescription());
		db.setInt(3, contract.getCompany().getId());
		db.setString(4, contract.getStatus());
		db.setString(5, contract.getType());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Contract contract) throws ModelException {

		DBHandler db = new DBHandler();

		String sqlUpdate = "UPDATE contract SET name = ?, description = ?, company_id = ?, status = ?, contract_type = ? where id = ?";

		db.prepareStatement(sqlUpdate);

		db.setString(1, contract.getName());
		db.setString(2, contract.getDescription());
		db.setInt(3, contract.getCompany().getId());
		db.setString(4, contract.getStatus());
		db.setString(5, contract.getType());
		db.setInt(6, contract.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Contract contract) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlDelete = " DELETE FROM contract WHERE id = ?";

		db.prepareStatement(sqlDelete);
		db.setInt(1, contract.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public List<Contract> listAll() throws ModelException {

		DBHandler db = new DBHandler();

		List<Contract> contract = new ArrayList<Contract>();

		// Declara uma instrução SQL
		String sqlQuery = "select c.id,c.name, c.description,  c.company_id, c.status, c.contract_type from contract c inner join companies co on c.company_id = co.id";

		db.createStatement();

		db.executeQuery(sqlQuery);

		while (db.next()) {
			Contract c = createContract(db);

			contract.add(c);
		}

		return contract;
	}

	@Override
	public Contract findById(int id) throws ModelException {

		DBHandler db = new DBHandler();

		String sql = "SELECT * FROM contract WHERE id = ?";

		db.prepareStatement(sql);

		db.setInt(1, id);
		db.executeQuery();

		Contract c = null;

		while (db.next()) {
			c = createContract(db);
			break;
		}

		return c;
	}

	private Contract createContract(DBHandler db) throws ModelException {
		Contract c = new Contract(db.getInt("id"));
		c.setName(db.getString("name"));
		c.setDescription(db.getString("description"));
		c.setStatus(db.getString("status"));
		c.setType(db.getString("contract_type"));

		CompanyDAO companyDAO = DAOFactory.createDAO(CompanyDAO.class);

		Company company = companyDAO.findById(db.getInt("company_id"));

		c.setCompany(company);

		return c;
	}
}
