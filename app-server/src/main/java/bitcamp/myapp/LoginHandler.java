package bitcamp.myapp;

import bitcamp.myapp.vo.User;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class LoginHandler {
    private HashMap<String, LoginUser> userMap = new HashMap<>();
    private HashMap<String, SessionId> sessionMap = new HashMap<>();
    Timer timer;

    public LoginHandler () {
        timer = new Timer();
        int period = 30 * 60 * 1000; // 30분(세션 인증 기간)
        timer.schedule(new RemoveTask(userMap,sessionMap),period,period);
    }

    public void addUser(String sessionId, User user) {
        LoginUser loginUser = new LoginUser(System.currentTimeMillis(),user);
        SessionId sesId = new SessionId(System.currentTimeMillis(),sessionId);
        userMap.put(sessionId,loginUser);
        sessionMap.put(loginUser.getUser().getEmail(),sesId);
    }

    public void removeUser(String sessionId) {
        LoginUser loginUser = userMap.get(sessionId);
        if (loginUser == null) {
            return;
        }
        sessionMap.remove(loginUser.getUser().getEmail());
        userMap.remove(sessionId);
    }


    public User getUser(String sessionId) {
        LoginUser loginUser = userMap.get(sessionId);
        if (loginUser == null) {
            return null;
        } else {
            if (System.currentTimeMillis() - loginUser.getTimestamp() > 30 * 60 * 1000) {
                // 30분 이상 경과시 null 반환
                return null;
            }
            return loginUser.getUser();
        }
    }

    public String getSessionId(String email) {
        if (sessionMap.get(email) == null) {
            return "";
        }
        return sessionMap.get(email).getSessionId();
    }
}

class RemoveTask extends TimerTask {
    private final Map<String, LoginUser> dataStructure1;
    private final Map<String, SessionId> dataStructure2;

    public RemoveTask(Map<String, LoginUser> dataStructure1, Map<String, SessionId> dataStructure2) {
        this.dataStructure1 = dataStructure1;
        this.dataStructure2 = dataStructure2;
    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        Iterator<Map.Entry<String, LoginUser>> iterator = dataStructure1.entrySet().iterator();
        Iterator<Map.Entry<String, SessionId>> iterator2 = dataStructure2.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, LoginUser> entry = iterator.next();
            LoginUser element = entry.getValue();
            long elementTimestamp = element.getTimestamp();
            if (currentTime - elementTimestamp >= 20 * 60 * 1000) {
                // 30분 이상 경과한 원소를 제거
                iterator.remove();
            }
        }

        while (iterator2.hasNext()) {
            Map.Entry<String, SessionId> entry = iterator2.next();
            SessionId element = entry.getValue();
            long elementTimestamp = element.getTimestamp();
            if (currentTime - elementTimestamp >= 20 * 60 * 1000) {
                // 30분 이상 경과한 원소를 제거
                iterator.remove();
            }
        }
    }
}

 class LoginUser {
    private long timestamp;
    private User user;

     public LoginUser(long timestamp, User user) {
         this.timestamp = timestamp;
         this.user = user;
     }

     public void setTimestamp(long timestamp) {
         this.timestamp = timestamp;
     }

     public void setUser(User user) {
         this.user = user;
     }

     public long getTimestamp() {
         return timestamp;
     }

     public User getUser() {
         return user;
     }
 }

 class SessionId {
    private long timestamp;
    private String sessionId;

     public SessionId(long timestamp, String sessionId) {
         this.timestamp = timestamp;
         this.sessionId = sessionId;
     }

     public long getTimestamp() {
         return timestamp;
     }

     public String getSessionId() {
         return sessionId;
     }

     public void setTimestamp(long timestamp) {
         this.timestamp = timestamp;
     }

     public void setSessionId(String sessionId) {
         this.sessionId = sessionId;
     }
 }
