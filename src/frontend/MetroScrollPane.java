package frontend;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MetroScrollPane extends ScrollPane {

    Pane zoomTarget;
    StackPane content;
    Group group;

    double max_scale;
    double min_scale;

    private double getZoom(ScrollEvent event) {
        double currentScale = zoomTarget.getScaleY();
        double zoomFactor;
        if (event.getDeltaY() > 0) {
            if (currentScale > max_scale) {
                return 1.0;
            }
            zoomFactor = 1.1;
        } else if (event.getDeltaY() < 0) {
            if (currentScale < min_scale) {
                return 1.0;
            }
            zoomFactor = 1 / 1.1;
        } else {
            return 1.0;
        }
        return zoomFactor;
    }

    public MetroScrollPane() {
        this(1200, 1200, "frontend/metro_map_image.png", 1.3, 1.7, 0.9);
    }

    MetroScrollPane(int viewport_width, int viewport_height, String image_filename,
                    double default_scale, double maxscale, double minscale) {
        max_scale = maxscale;
        min_scale = minscale;
        setPannable(true);

        ImageView imageView = new ImageView(new Image(image_filename));
        imageView.setFitHeight(viewport_height);
        imageView.setFitWidth(viewport_width);
        imageView.setPreserveRatio(true);
        // may still not give great results https://bugs.openjdk.java.net/browse/JDK-8089202
        imageView.setSmooth(true);

        zoomTarget = new Pane(imageView);
        zoomTarget.setPrefSize(viewport_width, viewport_height);
        zoomTarget.setScaleX(default_scale);
        zoomTarget.setScaleY(default_scale);

        group = new Group(zoomTarget);

        content = new StackPane(group);
        // resize content with parent
        viewportBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
            content.setPrefSize(newBounds.getWidth(), newBounds.getHeight());
        });
        setContent(content);

        /* From https://stackoverflow.com/a/38719541
         * Set up zooming for inner content
         * Pretty much the only way to do it properly around the mouse pointer. Takes offset into account
         */
        content.setOnScroll(event -> {
            event.consume();
            double zoomFactor = getZoom(event);
            Bounds groupBounds = group.getLayoutBounds();
            final Bounds viewportBounds = getViewportBounds();
            // calculate pixel offsets from [0, 1] range
            double valX = getHvalue() * (groupBounds.getWidth() - viewportBounds.getWidth());
            double valY = getVvalue() * (groupBounds.getHeight() - viewportBounds.getHeight());
            // convert content coordinates to zoomTarget coordinates
            Point2D parentCoords = new Point2D(event.getX(), event.getY());
            Point2D localCoordsZTarget = zoomTarget.parentToLocal(group.parentToLocal(parentCoords));
            // calculate adjustment of scroll position (pixels)
            Point2D adjustment = zoomTarget.getLocalToParentTransform().deltaTransform(localCoordsZTarget.multiply(zoomFactor - 1.0));

            // do the resizing
            zoomTarget.setScaleX(zoomFactor * zoomTarget.getScaleX());
            zoomTarget.setScaleY(zoomFactor * zoomTarget.getScaleY());
            // refresh ScrollPane scroll positions & content bounds
            layout();
            // convert back to [0, 1] range
            // (too large/small values are automatically corrected by ScrollPane)
            groupBounds = group.getLayoutBounds();
            setHvalue((valX + adjustment.getX()) / (groupBounds.getWidth() - viewportBounds.getWidth()));
            setVvalue((valY + adjustment.getY()) / (groupBounds.getHeight() - viewportBounds.getHeight()));

        });

    }
}
