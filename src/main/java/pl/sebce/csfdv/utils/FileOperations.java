package pl.sebce.csfdv.utils;

import pl.sebce.csfdv.domain.Project;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class FileOperations {

    public void saveProject(File file, Project project) {
        try {
            JAXBContext context = JAXBContext.newInstance(Project.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(project, file);
        } catch (Exception ex) {
            ex = getJAXBLinkedExceptionIfPresent(ex);
            throw new RuntimeException("Failed to save file: " + ex.getMessage(), ex);
        }
    }

    public Project loadProject(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(Project.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Project project = (Project) unmarshaller.unmarshal(file);
            return project;
        } catch (Exception ex) {
            ex = getJAXBLinkedExceptionIfPresent(ex);
            throw new RuntimeException("Failed to load file: " + ex.getMessage(), ex);
        }
    }

    private Exception getJAXBLinkedExceptionIfPresent(Exception ex) {
        if (ex instanceof JAXBException) {
            JAXBException jex = (JAXBException) ex;
            if (jex.getLinkedException() != null) {
                return (Exception) jex.getLinkedException();
            }
        }
        return ex;
    }
}