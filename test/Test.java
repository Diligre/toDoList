import com.diligre.entity.Project;
import com.diligre.repository.ProjectRepository;
import com.diligre.repository.TaskRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
public class Test {

    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    public void getTaskCountByProjectId(Long a){



    }


}
