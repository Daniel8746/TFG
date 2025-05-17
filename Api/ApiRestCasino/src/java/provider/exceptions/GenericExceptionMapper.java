/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package provider.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 *
 * @author danie
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

    private static final System.Logger LOG = System.getLogger(GenericExceptionMapper.class.getName());

    @Override
    public Response toResponse(Exception ex) {
        LOG.log(System.Logger.Level.ERROR, ex.getLocalizedMessage());
        return Response
                .status(Status.INTERNAL_SERVER_ERROR)
                .build();
    }
}
