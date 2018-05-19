package MemoryTreasure;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Controller {

    @FXML
    Button slideshow_button = new Button();

    @FXML
    StackPane main_StackPane = new StackPane();

    @FXML
    Button next_button = new Button();

    @FXML
    Button previous_button = new Button();

    @FXML
    VBox MainBox = new VBox();

    @FXML
    HBox buttonBox1 = new HBox();

    @FXML
    HBox buttonBox2 = new HBox();

    @FXML
    GridPane gridPane = new GridPane();

    @FXML
    Button next_page_button = new Button();

    @FXML
    Button previous_page_button = new Button();

    @FXML
    private ImageView imageView0_0 = new ImageView();

    @FXML
    private ImageView imageView0_1 = new ImageView();

    @FXML
    private ImageView imageView0_2 = new ImageView();

    @FXML
    private ImageView imageView0_3 = new ImageView();

    @FXML
    private ImageView imageView1_0 = new ImageView();

    @FXML
    private ImageView imageView1_1 = new ImageView();

    @FXML
    private ImageView imageView1_2 = new ImageView();

    @FXML
    private ImageView imageView1_3 = new ImageView();

    @FXML
    private ImageView imageView2_0 = new ImageView();

    @FXML
    private ImageView imageView2_1 = new ImageView();

    @FXML
    private ImageView imageView2_2 = new ImageView();

    @FXML
    private ImageView imageView2_3 = new ImageView();

    @FXML
    private ImageView imageView3_0 = new ImageView();

    @FXML
    private ImageView imageView3_1 = new ImageView();

    @FXML
    private ImageView imageView3_2 = new ImageView();

    @FXML
    private ImageView imageView3_3 = new ImageView();

    @FXML
    private ImageView main_imageView = new ImageView();

    @FXML
    private Label loading_label = new Label();

    @FXML
    private Label details_label_name = new Label();

    @FXML
    private Label details_label_height = new Label();

    @FXML
    private Label details_label_width = new Label();

    @FXML
    private Label details_label_location = new Label();

    @FXML
    private Label file_name_label = new Label();

    @FXML
    private ProgressBar progressBar = new ProgressBar();

    @FXML
    Tab imageTab = new Tab();

    @FXML
    Tab videoTab = new Tab();

    @FXML
    ListView videoView = new ListView();

    @FXML
    MediaView main_MediaView = new MediaView();

    @FXML
    private AnchorPane detailsPane = new AnchorPane();

    private ObservableList<ImageView> viewList = FXCollections.observableArrayList();
    private ObservableList<String> pathList = FXCollections.observableArrayList();
    private ObservableList<String> videoPaths = FXCollections.observableArrayList();
    private List<String> filePaths = new ArrayList<>();
    private Map<String, String> videoURLs = new HashMap<>();

    private int counter = 0;
    private static String currentImageDirectory;
    private static String currentVideoDirectory;
    private int currentImageIndex = 0;

    private boolean imageDirectory = false;
    private boolean videoDirectory = false;

    private List<String> images = new LinkedList<>();
    private List<String> videos = new LinkedList<>();


    public void initialize() {

        main_imageView.visibleProperty().bind(imageTab.selectedProperty());
        main_MediaView.visibleProperty().bind(videoTab.selectedProperty());

        viewList.add(0, imageView0_0);
        viewList.add(1, imageView0_1);
        viewList.add(2, imageView0_2);
        viewList.add(3, imageView0_3);
        viewList.add(4, imageView1_0);
        viewList.add(5, imageView1_1);
        viewList.add(6, imageView1_2);
        viewList.add(7, imageView1_3);
        viewList.add(8, imageView2_0);
        viewList.add(9, imageView2_1);
        viewList.add(10, imageView2_2);
        viewList.add(11, imageView2_3);
        viewList.add(12, imageView3_0);
        viewList.add(13, imageView3_1);
        viewList.add(14, imageView3_2);
        viewList.add(15, imageView3_3);

        main_imageView.setEffect(new DropShadow(20, Color.WHITE));

        details_label_name.setMaxWidth(detailsPane.getMaxWidth() - 20);
        details_label_height.setMaxWidth(detailsPane.getMaxWidth() - 20);
        details_label_width.setMaxWidth(detailsPane.getMaxWidth() - 20);
        details_label_location.setMaxWidth(detailsPane.getMaxWidth() - 20);

        details_label_name.setVisible(true);
        details_label_height.setVisible(true);
        details_label_width.setVisible(true);
        details_label_location.setVisible(true);
        
        for (int i = 0; i < 16; i++) {
            final int j = i;
            viewList.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (int k = 0; k < 16; k++) {
                        if (k == j)
                            viewList.get(j).setEffect(new DropShadow(40, Color.LIGHTBLUE));
                        else
                            viewList.get(k).setEffect(new DropShadow(20, Color.LIGHTGRAY));
                    }
                    currentImageIndex = j;
                    File temp = new File(pathList.get(j));
                    Image img = new Image(temp.toURI().toString(), 0, 0, true, true);
                    main_imageView.setImage(img);

                    details_label_name.setText("Name:\n" + temp.getName());
                    details_label_height.setText("Height: " + img.getHeight());
                    details_label_width.setText("Width: " + img.getWidth());
                    details_label_location.setText("Location:\n" + img.getUrl());

                }
            });
        }

        next_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (currentImageIndex == 15) {
                    counter += 16;
                    populateGallery(currentImageDirectory);
                    currentImageIndex = 0;
                    return;
                }

                viewList.get(currentImageIndex).setEffect(new DropShadow(20, Color.LIGHTGRAY));
                viewList.get(currentImageIndex + 1).setEffect(new DropShadow(40, Color.LIGHTBLUE));
                File temp = new File(pathList.get(currentImageIndex + 1));
                currentImageIndex++;
                Image img = new Image(temp.toURI().toString(), 0, 0, true, true);
                main_imageView.setImage(img);

                details_label_name.setText("Name:\n" + temp.getName());
                details_label_height.setText("Height: " + img.getHeight());
                details_label_width.setText("Width: " + img.getWidth());
                details_label_location.setText("Location:\n" + img.getUrl());
           }
        });

        previous_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (currentImageIndex == 0) {
                    counter -= 16;
                    populateGallery(currentImageDirectory);
                    currentImageIndex = 15;
                    return;
                }

                viewList.get(currentImageIndex).setEffect(new DropShadow(20, Color.LIGHTGRAY));
                viewList.get(currentImageIndex - 1).setEffect(new DropShadow(40, Color.LIGHTBLUE));
                File temp = new File(pathList.get(currentImageIndex - 1));
                currentImageIndex--;
                Image img = new Image(temp.toURI().toString(), 0, 0, true, true);
                main_imageView.setImage(img);

                details_label_name.setText("Name:\n" + temp.getName());
                details_label_height.setText("Height: " + img.getHeight());
                details_label_width.setText("Width: " + img.getWidth());
                details_label_location.setText("Location:\n" + img.getUrl());
            }
        });

        previous_page_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                counter -= 16;
                populateGallery(currentImageDirectory);
                currentImageIndex = 0;
            }
        });

        next_page_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                counter += 16;
                populateGallery(currentImageDirectory);
                currentImageIndex = 0;
            }
        });

        videoTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                details_label_height.setVisible(false);
                details_label_width.setVisible(false);
                details_label_location.setVisible(false);
                details_label_name.setVisible(false);
                buttonBox1.setDisable(true);
                buttonBox2.setDisable(true);
                buttonBox1.setVisible(false);
                buttonBox2.setVisible(false);
                detailsPane.setMinWidth(1000);
                detailsPane.setPrefWidth(1000);
                main_StackPane.setPrefSize(750, 600);
                main_StackPane.setLayoutY(50);
                main_MediaView.setFitWidth(960);
                main_MediaView.setFitHeight(900);
                main_MediaView.setPreserveRatio(true);
            }
        });

        imageTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                details_label_height.setVisible(true);
                details_label_location.setVisible(true);
                details_label_name.setVisible(true);
                details_label_width.setVisible(true);
                details_label_height.setText("Height");
                details_label_width.setVisible(true);
                details_label_width.setText("Width");
                details_label_name.setText("Name");
                details_label_location.setText("Location");
                detailsPane.setMinWidth(400);
                detailsPane.setPrefWidth(400);
                buttonBox1.setDisable(false);
                buttonBox2.setDisable(false);
                buttonBox1.setVisible(true);
                buttonBox2.setVisible(true);
                main_StackPane.setPrefSize(250, 150);
                main_StackPane.setLayoutY(64);
                main_MediaView.setFitWidth(360);
                main_MediaView.setFitHeight(250);
                main_MediaView.setPreserveRatio(false);
            }
        });

        slideshow_button.setText("Copy");
        slideshow_button.setTooltip(new Tooltip("Copies ALL the Photos/Videos present in separate folders." +
                                                        "\n(Currently unavailable.\nWill be working in the next version)"));

        gridPane.add(imageView0_0, 0, 0);
        gridPane.add(imageView0_1, 0, 1);
        gridPane.add(imageView0_2, 0, 2);
        gridPane.add(imageView0_3, 0, 3);
        gridPane.add(imageView1_0, 1, 0);
        gridPane.add(imageView1_1, 1, 1);
        gridPane.add(imageView1_2, 1, 2);
        gridPane.add(imageView1_3, 1, 3);
        gridPane.add(imageView2_0, 2, 0);
        gridPane.add(imageView2_1, 2, 1);
        gridPane.add(imageView2_2, 2, 2);
        gridPane.add(imageView2_3, 2, 3);
        gridPane.add(imageView3_0, 3, 0);
        gridPane.add(imageView3_1, 3, 1);
        gridPane.add(imageView3_2, 3, 2);
        gridPane.add(imageView3_3, 3, 3);

        writing_to_file();

        if(!images.isEmpty()){
            populateGallery(images.get(0));
        }
        
        if(!videos.isEmpty()){
            populateVideoGallery(videos.get(1));
        }
    }

    public void populateGallery(String dirPath) {

        Path dir = Paths.get(dirPath);

        int finalImageNumber = 16;

        if (!dir.toFile().exists()) {
            return;
        }

        // populateGallery("E:\\Images");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{png,jpg,gif}")) {

            for (Path entry : stream) {
                filePaths.add(entry.toString());
            }

            if (counter > filePaths.size()) {
                return;
            }

            if (counter < 0) {
                counter = 0;
            }
            if(!filePaths.isEmpty()) {
                imageDirectory = true;
                currentImageDirectory = dirPath;

                for (int i = 0; i < 16; i++) {
                    File file = new File(filePaths.get(i + counter));
                    pathList.add(i, file.toString());
                    Image image = makeImage(file.toURI().toString());

                    viewList.get(i).setImage(image);
                    viewList.get(i).setClip(null);
                    viewList.get(i).setEffect(new DropShadow(20, Color.LIGHTGRAY));

                    gridPane.setHalignment(viewList.get(i), HPos.CENTER);
                    gridPane.setValignment(viewList.get(i), VPos.CENTER);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            //ignore
        }
    }
    
    public void populateVideoGallery(String path){
        Path dir = Paths.get(path);
        
        if(!dir.toFile().exists()){
            return;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{mp4,amv,mkv}")) {

            for (Path entry : stream) {
                videoURLs.put(entry.getFileName().toString(), entry.toString());
                videoPaths.add(entry.getFileName().toString());
            }

            if(!videoPaths.isEmpty()){
                videoDirectory = true;
                currentVideoDirectory = path;
            }

            videoView.setItems(videoPaths);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            //ignore
        }
    }

    public Image makeImage(String path) {
        Image image = new Image(path, 170,
                120, false, true);

        ImageView imageView = new ImageView(image);

        Rectangle clip = new Rectangle(
                image.getWidth(), image.getHeight()
        );

        clip.setArcWidth(30);
        clip.setArcHeight(30);

        imageView.setClip(clip);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage limage = imageView.snapshot(parameters, null);

        return limage;

    }

    public void loadDialoge() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(MainBox.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Dialoge.fxml"));

        try {
            dialog.getDialogPane().setContent(loader.load());

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            controller_2 controller = loader.getController();

            populateGallery(controller.getDirectory());
            populateVideoGallery(controller.getDirectory());

            writing_to_file();

        } else {
        }
    }

    public void writing_to_file() {
        int progress = 1;

        try(BufferedReader reader = Files.newBufferedReader(Paths.get("imageData.txt"))){
            String s;
            while((s = reader.readLine()) != null){
                images.add(s);
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        try(BufferedReader reader = Files.newBufferedReader(Paths.get("videoData.txt"))){
            String s;
            while((s = reader.readLine()) != null){
                videos.add(s);
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        if(imageDirectory) {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("imageData.txt"), StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {

                if (images.contains(currentImageDirectory)) {

                } else {
                    writer.write(currentImageDirectory + "\n");
                    progress++;
                }

            } catch (IOException e) {
              }
        }

        if(videoDirectory) {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("videoData.txt"), StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {

                if (videos.contains(currentImageDirectory)) {
              //      System.out.println(currentImageDirectory + " // skipped");
                }
                writer.write(currentImageDirectory + "\n");
//                System.out.println(currentImageDirectory + " // added");
//                System.out.println(progress);
                  progress++;

            } catch (IOException e) {
                //System.out.println(e.getMessage());
            }
        }
    }

    public void playVideo(){
        String name = (String) videoView.getSelectionModel().getSelectedItem();

        //System.out.println(videoURLs.get(name));

        Path path = Paths.get(videoURLs.get(name));

        Media media = new Media(path.toUri().toString());

        MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);

        main_MediaView.setMediaPlayer(player);

        details_label_name.setText("Name:\n" + name);
        details_label_height.setText("Duration: " + media.getDuration().toString());
    }
}

