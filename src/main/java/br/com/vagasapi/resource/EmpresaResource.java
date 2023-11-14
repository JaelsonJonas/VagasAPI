package br.com.vagasapi.resource;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import br.com.vagasapi.dto.ErrorResponse;
import br.com.vagasapi.exceptions.EmpresaException;
import br.com.vagasapi.model.Empresa;
import br.com.vagasapi.service.EmpresaService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

@Path("empresa")
public class EmpresaResource {

	private EmpresaService service;

	public EmpresaResource() throws SQLException {
		service = new EmpresaService();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAll() throws SQLException {

		List<Empresa> empresas = service.listar();

		return Response.ok().entity(empresas).build();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmpresabyId(@PathParam("id") long id) {

		try {
		
			return Response.ok().entity(service.empresaPorId(id)).build();
		
		} catch (EmpresaException | NoSuchElementException e) {
			
			e.printStackTrace();
		
			return Response.status(Status.NOT_FOUND).entity(new ErrorResponse("Empresa not found")).build();

		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Empresa empresa, @Context UriInfo uriInfo) {

		UriBuilder builder = null;

		builder = uriInfo.getAbsolutePathBuilder();

		builder.path(Long.toString(service.cadastrar(empresa).getId()));

		return Response.created(builder.build()).entity(empresa).build();

	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") long id) {
		int delete = service.deletar(id);

		if (delete <= 0)
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Empresa not found")).build();

		return Response.noContent().build();
	}

	@PUT
	@Path("{id}")
	public Response update(@PathParam("id") int id, Empresa empresa) {

		Empresa update = null;

		try {
			update = service.atualizar(empresa, id);
		} catch (EmpresaException e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();

		}

		return Response.ok().entity(update).build();
	}

}
