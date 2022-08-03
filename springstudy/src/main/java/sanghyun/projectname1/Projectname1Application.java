package sanghyun.projectname1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication      //여기서 boot 시작. spring이 하위를 뒤져나간다.
//따라서 해당 코드 포함된 패키지 ( sanghyun.projectname1 )  안의 요소들만 컴포넌트 스캔한다
public class Projectname1Application {     //gradlew build   >> build in cmd

	public static void main(String[] args) {
		SpringApplication.run(Projectname1Application.class, args);
	}

}
