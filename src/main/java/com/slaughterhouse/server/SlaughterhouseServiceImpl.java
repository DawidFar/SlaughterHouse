package com.slaughterhouse.server;

import com.slaughterhouse.grpc.SlaughterhouseServiceGrpc;
import com.slaughterhouse.grpc.SlaughterhouseProto.*;
import io.grpc.stub.StreamObserver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SlaughterhouseServiceImpl extends SlaughterhouseServiceGrpc.SlaughterhouseServiceImplBase {
    private final Database db;

    public SlaughterhouseServiceImpl(Database db) {
        this.db = db;
    }

    @Override
    public void getAnimalsByProduct(ProductRequest request, StreamObserver<AnimalList> responseObserver) {
        List<Animal> animals = new ArrayList<>();
        String sql = """ 
            SELECT DISTINCT a.animalid, a.registrationnumber
            FROM animal a
            JOIN part p ON a.animalid = p.animalid
            JOIN tray t ON p.trayid = t.trayid
            JOIN producttray pt ON t.trayid = pt.trayid
            WHERE pt.productid = ?
        """;
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, request.getProductId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    animals.add(Animal.newBuilder()
                            .setId(rs.getInt("animalid"))
                            .setRegistrationNumber(rs.getString("registrationnumber"))
                            .build());
                }
            }
        } catch (SQLException e) {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
            return;
        }
        AnimalList list = AnimalList.newBuilder().addAllAnimals(animals).build();
        responseObserver.onNext(list);
        responseObserver.onCompleted();
    }

    @Override
    public void getProductsByAnimal(AnimalRequest request, StreamObserver<ProductList> responseObserver) {
        List<Product> products = new ArrayList<>();
        String sql = """ 
            SELECT DISTINCT p.productid, p.name
            FROM product p
            JOIN producttray pt ON p.productid = pt.productid
            JOIN tray t ON pt.trayid = t.trayid
            JOIN part pa ON t.trayid = pa.trayid
            WHERE pa.animalid = ?
        """;
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, request.getAnimalId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(Product.newBuilder()
                            .setId(rs.getInt("productid"))
                            .setName(rs.getString("name"))
                            .build());
                }
            }
        } catch (SQLException e) {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
            return;
        }
        ProductList list = ProductList.newBuilder().addAllProducts(products).build();
        responseObserver.onNext(list);
        responseObserver.onCompleted();
    }
}
