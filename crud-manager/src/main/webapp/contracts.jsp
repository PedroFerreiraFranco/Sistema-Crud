<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<%@include file="base-head.jsp"%>
<title>CRUD Manager - Contratos</title>
</head>
<body>
	<%@include file="modal.html"%>
	<%@include file="nav-menu.jsp"%>

	<div id="container" class="container-fluid">
		<div id="alert"
			style="${not empty message ? 'display: block;' : 'display: none;'}"
			class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			${message}
		</div>

		<div id="top" class="row">
			<div class="col-md-3">
				<h3>Contratos</h3>
			</div>

			<div class="col-md-6">
				<div class="input-group h2">
					<input name="data[search]" class="form-control" id="search"
						type="text" placeholder="Pesquisar Contratos"> <span
						class="input-group-btn">
						<button class="btn btn-danger" type="submit">
							<span class="glyphicon glyphicon-search"></span>
						</button>
					</span>
				</div>
			</div>

			<div class="col-md-3">
				<a href="/crud-manager/contract/form"
					class="btn btn-danger pull-right h2"><span
					class="glyphicon glyphicon-plus"></span>&nbspAdicionar Contrato</a>
			</div>
		</div>

		<hr />

		<div id="list" class="row">
			<div class="table-responsive col-md-12">
				<table class="table table-striped table-hover" cellspacing="0"
					cellpadding="0">

					<thead>
						<tr>
							<th>Nome</th>
							<th>Descrição</th>
							<th>Status</th>
							<th>Tipo</th>
							<th>Empresa</th>
							<th>Editar</th>
							<th>Excluir</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="contract" items="${contracts}">
							<tr>
								<td>${contract.getName()}</td>
								<td>${contract.getDescription()}</td>
								<td>${contract.getStatus()}</td>
								<td>${contract.getType()}</td>
								<td>${contract.getCompany().getName()}</td>
								<td class="actions"><a class="btn btn-info btn-xs"
									href="${pageContext.request.contextPath}/contract/update?contractId=${contract.getId()}">
										<span class="glyphicon glyphicon-edit"></span>
								</a></td>
								<td class="actions"><a
									class="btn btn-danger btn-xs modal-remove"
									data-contract-id="${contract.getId()}"
									data-contract-name="${contract.getName()}" data-toggle="modal"
									data-target="#delete-modal" href="#"><span
										class="glyphicon glyphicon-trash"></span></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<div id="bottom" class="row">
				<div class="col-md-12">
					<ul class="pagination">
						<li class="disabled"><a>&lt; Anterior</a></li>
						<li class="disabled"><a>1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li class="next"><a href="#" rel="next">Próximo &gt;</a></li>
					</ul>
					<!-- /.pagination -->
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(document).ready(
				function() {
					// fecha o alert após 3 segundos
					setTimeout(function() {
						$("#alert").slideUp(500);
					}, 3000);

					$(".modal-remove").click(
							function() {
								var contractName = $(this).attr(
										'data-contract-name');
								var contractId = $(this).attr('data-contract-id');
								$(".modal-body #hiddenValue").text(
										"o contrato '" + contractName + "'");
								$("#id").attr("value", contractId);
								$("#entityName").attr("value", contractName);
								$("#form").attr("action", "contract/delete");
							})
				});
	</script>
</body>
</html>