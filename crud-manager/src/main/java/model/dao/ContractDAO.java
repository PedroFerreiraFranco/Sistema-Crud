package model.dao;

import java.util.List;

import model.Contract;
import model.ModelException;

public interface ContractDAO {

	boolean save(Contract contract) throws ModelException;

	boolean update(Contract contract) throws ModelException;

	boolean delete(Contract contract) throws ModelException;

	List<Contract> listAll() throws ModelException;

	Contract findById(int id) throws ModelException;

}
