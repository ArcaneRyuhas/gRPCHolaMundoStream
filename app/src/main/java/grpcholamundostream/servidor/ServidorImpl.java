package grpcholamundostream.servidor;

import java.util.Scanner;

import com.proto.saludo.Holamundo.ArchivoRequest;
import com.proto.saludo.Holamundo.ArchivoResponse;
import com.proto.saludo.Holamundo.SaludoRequest;
import com.proto.saludo.Holamundo.SaludoResponse;
import com.proto.saludo.SaludoServiceGrpc.SaludoServiceImplBase;

import io.grpc.stub.StreamObserver;

public class ServidorImpl extends SaludoServiceImplBase{
    @Override
    public void saludo(SaludoRequest request, StreamObserver<SaludoResponse> responseObserver){
        //Se construye la respuesta a enviarle al cliente
        SaludoResponse respuesta = SaludoResponse
        .newBuilder()
        .setResultado("Hola " + request.getNombre())
        .build();

        responseObserver.onNext(respuesta);

        responseObserver.onCompleted();
    }

    @Override
    public void saludoStream (SaludoRequest request, StreamObserver<SaludoResponse> responseObserver){
        for (int i = 0; i < 10 ; i++){
            SaludoResponse respuesta = SaludoResponse
                .newBuilder()
                .setResultado("Hola " + request.getNombre() + "por: " + i + " vez.")
                .build();

            responseObserver.onNext(respuesta);
        }

        responseObserver.onCompleted();
    }

    @Override
    public void archivoStream (ArchivoRequest request, StreamObserver<ArchivoResponse> responseObserver){
        try (Scanner scanner = new Scanner(ServidorImpl.class.getResourceAsStream("/archivote.csv"))) {
            while (scanner.hasNextLine()) {
                ArchivoResponse respuesta = ArchivoResponse
                    .newBuilder()
                    .setResultado(scanner.nextLine())
                    .build();

                responseObserver.onNext(respuesta);
            }

            responseObserver.onCompleted();
        }
    }
}
