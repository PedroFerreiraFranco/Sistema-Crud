package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Company;
import model.Contract;
import model.ModelException;
import model.dao.CompanyDAO;
import model.dao.ContractDAO;
import model.dao.DAOFactory;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/contracts", "/contract/form", "/contract/insert", "/contract/update", "/contract/delete" })
public class ContractsController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getRequestURI();

		switch (action) {
		case "/crud-manager/contract/form": {
			loadCompanies(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-contract.jsp");
			break;
		}
		case "/crud-manager/contract/update": {
			loadContract(req);
			loadCompanies(req);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-contract.jsp");
			break;
		}
		default:
			listContracts(req);

			ControllerUtil.transferSessionMessagesToRequest(req);

			ControllerUtil.forward(req, resp, "/contracts.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getRequestURI();

		switch (action) {
		case "/crud-manager/contract/insert": {
			insertContract(req);
			ControllerUtil.redirect(resp, req.getContextPath() + "/contracts");
			break;
		}
		case "/crud-manager/contract/update": {
			updateContract(req);
			ControllerUtil.redirect(resp, req.getContextPath() + "/contracts");
			break;
		}
		case "/crud-manager/contract/delete": {
			deleteContract(req);
			ControllerUtil.redirect(resp, req.getContextPath() + "/contracts");
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	private void deleteContract(HttpServletRequest req) {
		String contractIdStr = req.getParameter("id");
		int contractId = Integer.parseInt(contractIdStr);

		String contractName = req.getParameter("entityName");

		ContractDAO dao = DAOFactory.createDAO(ContractDAO.class);

		try {
			if (dao.delete(new Contract(contractId))) {
				ControllerUtil.sucessMessage(req, "Contrato '" + contractName + "' excluído com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Contrato '" + contractName + "' não pode ser excluído.");
			}
		} catch (ModelException e) {
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertContract(HttpServletRequest req) {

		Contract contract = createContract(req, 0);

		ContractDAO dao = DAOFactory.createDAO(ContractDAO.class);

		try {
			if (dao.save(contract))
				ControllerUtil.sucessMessage(req, "Contrato " + contract.getName() + " salvo com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Contrato " + contract.getName() + " não pode ser salvo.");
		} catch (ModelException e) {
			e.printStackTrace(); // log
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updateContract(HttpServletRequest req) {

		String contractIdStr = req.getParameter("contract_id");
		int contractId = Integer.parseInt(contractIdStr);

		Contract contract = createContract(req, contractId);

		ContractDAO dao = DAOFactory.createDAO(ContractDAO.class);

		try {
			if (dao.update(contract))
				ControllerUtil.sucessMessage(req, "Contrato " + contract.getName() + " alterado com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Contrato " + contract.getName() + " não pode ser alterado.");
		} catch (ModelException e) {
			e.printStackTrace(); // log
			ControllerUtil.errorMessage(req, e.getMessage());
		}

	}

	private void loadContract(HttpServletRequest req) {
		String contractIdStr = req.getParameter("contractId");
		int contractId = Integer.parseInt(contractIdStr);

		ContractDAO dao = DAOFactory.createDAO(ContractDAO.class);

		Contract contract = new Contract(0);

		try {
			contract = dao.findById(contractId);
		} catch (ModelException e) {
			ControllerUtil.errorMessage(req, "Erro ao carregar vendedor para edição.");
		}

		req.setAttribute("contractToEdit", contract);
	}

	private void loadCompanies(HttpServletRequest req) {
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		List<Company> companies = new ArrayList<>();
		try {
			companies = dao.listAll();
		} catch (ModelException e) {
			ControllerUtil.errorMessage(req, "Erro ao carregar as empresas.");
		}
		req.setAttribute("companies", companies);
	}

	private void listContracts(HttpServletRequest req) {

		ContractDAO dao = DAOFactory.createDAO(ContractDAO.class);

		List<Contract> contracts = new ArrayList<>();

		try {
			contracts = dao.listAll();
		} catch (ModelException e) {
			ControllerUtil.errorMessage(req, "Erro ao carregar dados dos contratos.");
		}

		req.setAttribute("contracts", contracts);
	}

	private Contract createContract(HttpServletRequest req, int contractId) {

		String contractName = req.getParameter("contract_name");
		String contractDescription = req.getParameter("contract_description");
		String contractStatus = req.getParameter("contract_status");
		String contractType = req.getParameter("contract_contract_type");
		String contractCompany = req.getParameter("contract_company_id");
		int contractCompanyId = Integer.parseInt(contractCompany);

		Contract contract;
		if (contractId == 0) {
			contract = new Contract();
		} else {
			contract = new Contract(contractId);
		}
		contract.setName(contractName);
		contract.setDescription(contractDescription);
		contract.setStatus(contractStatus);
		contract.setType(contractType);

		contract.setCompany(new Company(contractCompanyId));

		return contract;
	}

}
