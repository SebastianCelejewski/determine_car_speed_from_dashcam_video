package pl.sebcel.csfdv.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Project {

    @XmlElement
    private String imagesDirectory;

    @XmlElement
    private Integer fps;

    @XmlElement
    private Integer referencePointDistance;

    @XmlElement
    private List<Integer> selectedFrames = new ArrayList<Integer>();

   public List<Integer> getSelectedFrames() {
        return selectedFrames;
    }

    public void setSelectedFrames(List<Integer> selectedFrames) {
        this.selectedFrames = selectedFrames;
    }

    public String getImagesDirectory() {
        return imagesDirectory;
    }

    public void setImagesDirectory(String imagesDirectory) {
        this.imagesDirectory = imagesDirectory;
    }

    public Integer getFps() {
        return fps;
    }

    public void setFps(Integer fps) {
        this.fps = fps;
    }

    public Integer getReferencePointDistance() {
        return referencePointDistance;
    }

    public void setReferencePointDistance(Integer referencePointDistance) {
        this.referencePointDistance = referencePointDistance;
    }
}