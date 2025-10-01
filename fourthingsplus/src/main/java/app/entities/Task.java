package app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Task {
    private int taskId;
    private String name;
    private boolean isDone = false;
}
