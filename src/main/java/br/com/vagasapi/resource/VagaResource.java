package br.com.vagasapi.resource;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import br.com.vagasapi.dto.ErrorResponse;
import br.com.vagasapi.dto.VagaResponse;
import br.com.vagasapi.exceptions.VagaException;
import br.com.vagasapi.model.Vaga;
import br.com.vagasapi.service.VagaService;
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

@Path("vaga")
public class VagaResource {

	private VagaService service;

	public VagaResource() throws SQLException {
		service = new VagaService();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAll() throws SQLException {
		List<VagaResponse> vagas = service.listar().stream().map(VagaResponse::new).toList();

		return Response.ok().entity(vagas).build();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVagabyId(@PathParam("id") long id) {

		try {
			return Response.ok().entity(new VagaResponse(service.getById(id))).build();
		} catch (VagaException | NoSuchElementException e) {
			e.printStackTrace();
			return Response.status(Status.NOT_FOUND).entity(new ErrorResponse("Vaga not found")).build();

		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Vaga vaga, @Context UriInfo uriInfo) {
		UriBuilder builder = null;
		builder = uriInfo.getAbsolutePathBuilder();
		builder.path(Long.toString(service.cadastrar(vaga).getId()));

		return Response.created(builder.build()).entity(new VagaResponse(vaga)).build();
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") long id) {
		int delete = service.deletar(id);

		if (delete <= 0)
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse("Vaga not found")).build();

		return Response.noContent().build();
	}

	@PUT
	@Path("{id}")
	public Response update(@PathParam("id") int id, Vaga vaga) {

		Vaga update = null;
		try {
			update = service.atualizar(vaga, id);
		} catch (VagaException e) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
		}

		return Response.ok().entity(new VagaResponse(update)).build();

	}

}
