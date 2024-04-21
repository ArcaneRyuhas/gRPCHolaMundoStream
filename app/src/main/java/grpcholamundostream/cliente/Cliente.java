package grpcholamundostream.cliente;

import com.proto.saludo.SaludoServiceGrpc;
import com.proto.saludo.Holamundo.ArchivoRequest;
import com.proto.saludo.Holamundo.SaludoRequest;
import com.proto.saludo.Holamundo.SaludoResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Cliente {
    
    public static void main(String [] args){
        String host = "localhost";
        int puerto = 8080;

        ManagedChannel ch = ManagedChannelBuilder.forAddress(host, puerto).usePlaintext().build();

        //saludarUnario(ch);
        saludarStream(ch);
        //archivoStream(ch);

        System.out.println("Apagando...");
        ch.shutdown();

    }

    private static void saludarStream(ManagedChannel ch) {
        SaludoServiceGrpc.SaludoServiceBlockingStub stub = SaludoServiceGrpc.newBlockingStub(ch);
        SaludoRequest peticion = SaludoRequest.newBuilder().setNombre("Mario Portilla").build();
        
        stub.saludoStream(peticion).forEachRemaining(respuesta ->{
            System.out.println("Respuesta gRPC: " + respuesta.getResultado());
        });
    }

    private static void saludarUnario(ManagedChannel ch) {
        SaludoServiceGrpc.SaludoServiceBlockingStub stub = SaludoServiceGrpc.newBlockingStub(ch);
        SaludoRequest peticion = SaludoRequest.newBuilder().setNombre("Mario Portilla").build();
        SaludoResponse respuesta = stub.saludo(peticion);

        System.out.println("Respuesta gRPC: " + respuesta.getResultado());
    }
    
    private static void archivoStream(ManagedChannel ch) {
        SaludoServiceGrpc.SaludoServiceBlockingStub stub = SaludoServiceGrpc.newBlockingStub(ch);
        ArchivoRequest peticion = ArchivoRequest.newBuilder().setResultado("Mario Portilla").build();
        
        System.out.println("Respuesta gRPC: ");
        System.out.println("");

        stub.archivoStream(peticion).forEachRemaining(respuesta ->{
            System.out.print(respuesta.getResultado());
        });
    }
}

