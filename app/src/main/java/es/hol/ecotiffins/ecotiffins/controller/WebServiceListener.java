package es.hol.ecotiffins.ecotiffins.controller;

import java.io.IOException;

import es.hol.ecotiffins.ecotiffins.model.WebService;

public interface WebServiceListener {
    /**
     * This method will be invoked on a successful http request
     * @param response
     * It is the response received from the server after request.
     * @param api
     * This argument has been added to identify which API has been called,
     * These constants has been defined in in.teramatrix.vota.transporter.model.WebService.class
     * @see WebService
     */
    void onRequestCompleted(String response, int api);

    /**
     * This method will invoke on Http Request failure
     * @param e
     * It is the exception arised during http request
     * @param api
     * This argument has been added to identify which API has been called,
     * These constants has been defined in in.teramatrix.vota.transporter.model.WebService.class
     * @see WebService
     */
    void onRequestFailure(IOException e, int api);
}