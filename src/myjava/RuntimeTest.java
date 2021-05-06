package myjava;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RuntimeTest {
	public static void main(String[] args) throws IOException, InterruptedException {
		Runtime rt = Runtime.getRuntime();
		Process pc = null;
		try {
			//외부 프로세스 실행
			pc = rt.exec("cmd /c dir /w");
			BufferedReader stdOut = new BufferedReader( new InputStreamReader(pc.getInputStream()) );
			String str;
			while( (str = stdOut.readLine()) != null ) {
				System.out.println(str);
			}
			System.out.println(pc);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//명령어 종료시 까지 대기
			System.out.println(pc);
			pc.waitFor();
			System.out.println(pc);
			System.out.println("프로그램 종료 대기중");
			
			//명령어 종료시 하위 프로세스 제거
			System.out.println(pc);
			pc.destroy();
			System.out.println("프로그램 종료됨");
		}
//		Process process = new ProcessBuilder("cmd", "/c", "dir", "/w").start();
//		BufferedReader stdOut = new BufferedReader( new InputStreamReader(process.getInputStream()) );
//
//		String str;
//		// 표준출력 상태를 출력
//		while( (str = stdOut.readLine()) != null ) {
//		    System.out.println(str);
//		}
	}
}
