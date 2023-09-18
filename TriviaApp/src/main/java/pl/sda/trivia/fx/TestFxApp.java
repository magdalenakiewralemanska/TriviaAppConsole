package pl.sda.trivia.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TestFxApp extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Rectangle rectangle = new Rectangle(100, 100, 100, 200); // współrzędne lewego górnego rogu, szerokość i wysokość
        rectangle.setFill(Color.GREEN);
        rectangle.setStroke(Color.BROWN);
        rectangle.setStrokeWidth(2);
        Circle circle = new Circle(200, 200, 100);
        circle.setFill(Color.BLUE);
        circle.setStroke(Color.YELLOW);
        circle.setStrokeWidth(4);
        Circle circle1 = new Circle(200, 200, 50);
        circle1.setFill(Color.YELLOW);
        circle1.setStroke(Color.RED);
        circle1.setStrokeWidth(3);
        Arc arc = new Arc();
        arc.setCenterX(300);
        arc.setCenterY(300);
        arc.setRadiusX(50);
        arc.setRadiusY(50);
        arc.setStartAngle(90);
        arc.setLength(90);
        arc.setStrokeWidth(5);
        //dodanie do root'a
//        root.getChildren().add(rectangle);
//        root.getChildren().add(circle);
        Text text = new Text("Hello Java Fx");
        text.setX(10);
        text.setY(30);
        Font lato = Font.font("Lato", 36);
        text.setFont(lato);
        text.setFill(Color.AQUAMARINE);
        Line line = new Line(20, 20, 480, 380);
        line.setStroke(Color.BLUEVIOLET);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setStrokeWidth(20);
        Path path =new Path();
        path.getElements().addAll(
                new MoveTo(400, 0),
                new LineTo(300, 100),
                new LineTo(300, 200),
                new LineTo(400,0)
        );
        path.setStroke(Color.DARKMAGENTA);
        path.setStrokeWidth(2);
        root.getChildren().addAll(rectangle, circle, circle1, text, arc, line, path);
        Scene scene = new Scene(root, 500, 400);
        stage.setTitle("Test.JavaFx");
        stage.setScene(scene);
        stage.show();
    }
}
