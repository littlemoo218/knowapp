package north.helper;

import java.util.ArrayList;

public interface login_callback {
    void on_no_user();
    void on_wrong_pwd();
    void on_success(ArrayList<String> fri, String login_id);
}
