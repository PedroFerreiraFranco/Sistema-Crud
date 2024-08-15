<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<%@include file="base-head.jsp"%>
<title>CRUD Manager - Inserir Contrato</title>
</head>
<body>
	<%@include file="nav-menu.jsp"%>

	<div id="container" class="container-fluid">
		<h3 class="page-header">${action eq "insert" ? "Adicionar" : "Editar"}
			Contrato</h3>

		<form action="${pageContext.request.contextPath}/contract/${action}"
			method="POST">

			<input type="hidden" name="contract_id" value="${contractToEdit.getId()}">

			<div class="row">
				<div class="form-group col-md-6">
					<label for="contract_name">Nome</label> <input type="text"
						class="form-control" id="contract_name" name="contract_name"
						autofocus="autofocus" placeholder="Nome do Contrato" required
						oninvalid="this.setCustomValidity('Por favor, informe o Nome do Contrato.')"
						oninput="setCustomValidity('')" value="${contractToEdit.getName()}">
				</div>

				<div class="form-group col-md-6">
					<label for="content">Description</label> <input type="text"
						class="form-control" id="contract_description" name="contract_description"
						autofocus="autofocus" placeholder="Descrição do Contrato" required
						oninvalid="this.setCustomValidity('Por favor, informe a Descrição do Contrato.')"
						oninput="setCustomValidity('')" value="${contractToEdit.getDescription()}">
				</div>
				
				<div class="form-group col-md-6">
					<label for="content">Status</label> <input type="text"
						class="form-control" id="contract_status" name="contract_status"
						autofocus="autofocus" placeholder="Status do Contrato" required
						oninvalid="this.setCustomValidity('Por favor, informe o Status do Contrato.')"
						oninput="setCustomValidity('')" value="${contractToEdit.getStatus()}">
				</div>
				
				<div class="form-group col-md-6">
					<label for="content">Tipo</label> <input type="text"
						class="form-control" id="contract_contract_type" name="contract_contract_type"
						autofocus="autofocus" placeholder="Tipo do Contrato" required
						oninvalid="this.setCustomValidity('Por favor, informe o Tipo do Contrato.')"
						oninput="setCustomValidity('')" value="${contractToEdit.getType()}">
				</div>

				<div class="form-group col-md-6">
					<label for="contract_company">Empresa</label> <select
						id="contract_company_id" class="form-control selectpicker"
						name="contract_company_id" required
						oninvalid="this.setCustomValidity('Por favor, informe a Empresa.')"
						oninput="setCustomValidity('')">
						<option value=""disabled ${notemptycontractToEdit ? "" : "selected"}>Selecione
							uma empresa</option>
						<c:forEach var="company" items="${companies}">
							<option value="${company.getId()}"
								${contractToEdit.getCompany().getId() eq company.getId() ? "selected" : ""}>
								${company.getName()}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<hr />

			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					<a href="${pageContext.request.contextPath}/contracts"
						class="btn btn-default">Cancelar</a>
					<button type="submit" class="btn btn-primary">${action eq "insert" ? "Criar" : "Editar"}
						Contratos</button>
				</div>
			</div>

		</form>
	</div>

	<!-- IMPORTAR OS SCRIPTS -->
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>