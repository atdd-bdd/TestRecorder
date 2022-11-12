package kenpugh.TestRecorder.Entities;

public class TestForEquals {
    public boolean issueIDCheck = false;
    public boolean nameCheck = false;
    public boolean lastResultCheck = false;
    public boolean runnerCheck = false;
    public boolean dateLastRunCheck = false;
    public boolean datePreviousResultCheck = false;
    public boolean filePathCheck = false;
    public boolean commentsCheck = false;

    @Override
    public String toString() {
        return "TestForEquals{" +
                "issueIDCheck=" + issueIDCheck +
                ", nameCheck=" + nameCheck +
                ", lastResultCheck=" + lastResultCheck +
                ", runnerCheck=" + runnerCheck +
                ", dateLastRunCheck=" + dateLastRunCheck +
                ", datePreviousResultCheck=" + datePreviousResultCheck +
                ", filePathCheck=" + filePathCheck +
                ", commentsCheck=" + commentsCheck +
                '}';
    }
   public void setFromTestDTO(TestDTO testDTO){
        issueIDCheck = (testDTO.issueID != null);
        filePathCheck =  (testDTO.filePath != null);
        dateLastRunCheck = (testDTO.dateLastRun != null) ;
        datePreviousResultCheck = (testDTO.datePreviousResult != null);
        lastResultCheck = (testDTO.lastResult != null) ;
        commentsCheck = (testDTO.comments != null);
        runnerCheck = (testDTO.runner != null);
        nameCheck = (testDTO.name != null);
    }
}