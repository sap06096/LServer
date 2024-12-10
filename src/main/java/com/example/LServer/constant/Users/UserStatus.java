package com.example.LServer.constant.Users;

import com.example.LServer.constant.LegacyCommonType;
import com.example.LServer.module.common.Generics;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 회원 상태
 * <br><br><b>values:</b>
 * <br> 1 = WAIT : 예비(활동 대기)
 * <br> 2 = ACTIVE : 정상(활성화) 상태
 * <br> 3 = PAUSE : 이용정지(징계) 상태
 * <br> 4 = EXPIRE_RESERVED : 탈퇴 (자료 보존 중)
 * <br> 5 = EXPIRED : 탈퇴/소멸
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserStatus implements LegacyCommonType {
	USER_STATUS_NONE(0,"NONE"),
	USER_STATUS_WAIT(1, "예비(활동 대기)"),
	USER_STATUS_ACTIVE(2,"ACTIVE : 정상(활성화) 상태"),
	USER_STATUS_PAUSE(3,"PAUSE : 이용정지(징계) 상태"),
	USER_STATUS_EXPIRE_RESERVED(4,"EXPIRE_RESERVED : 탈퇴 (자료 보존 중)"),
	USER_STATUS_EXPIRED(5,"EXPIRED : 탈퇴/소멸"),
	USER_STATUS_UNFIXED(6, "월정액 미납"),
	;

	private final int value;
	private final String name;

	UserStatus(int val, String name) {
		this.value = val;
		this.name = name;
	}

	public Integer getValue() {
		return this.value;
	}
	public String getName() {
		return this.name;
	}
	public String getLabel() {
		return this.name;
	}

	@JsonCreator
	public static UserStatus byCheck(Integer value, String name) {
		for(UserStatus S : values())
			if(Objects.equals(S.getValue(), value)) return S;
		return null;
	}

	public static UserStatus byValue(UserStatus val){
		for(UserStatus S : values()) if(S==val) return S;
		return null;
	}

	public static UserStatus byValue(int val){
		for(UserStatus S : values()) if(S.getValue()==val) return S;
		return null;
	}

	public static List<UserStatus> byArrayValue(int[] values) {
		List<UserStatus> returnArray = Generics.newArrayList();
		for (UserStatus userStatus : values()) {
			for (int val : values) {
				if (userStatus.getValue() == val) {
					returnArray.add(userStatus);
				}
			}
		}
		return returnArray;
	}

	public static List<UserStatus> getFull(){
		return Arrays.asList(USER_STATUS_NONE, USER_STATUS_WAIT, USER_STATUS_ACTIVE, USER_STATUS_PAUSE, USER_STATUS_EXPIRE_RESERVED, USER_STATUS_EXPIRED);
	}
	public static List<Integer> getFullInt(){
		return Arrays.asList(USER_STATUS_NONE.getValue(), USER_STATUS_WAIT.getValue(), USER_STATUS_ACTIVE.getValue(), USER_STATUS_PAUSE.getValue(), USER_STATUS_EXPIRE_RESERVED.getValue(), USER_STATUS_EXPIRED.getValue());
	}

	/**
	 * 정상적인 서비스를 이용할 수 있는 상태
	 * @return int[] 상태값의 목록
	 */
	public static UserStatus[] getValid(){
		return new UserStatus[]{ USER_STATUS_ACTIVE };
	}

	public static UserStatus[] getPause(){
		return new UserStatus[]{ USER_STATUS_PAUSE};
	}

	/**
	 * 정상적인 서비스를 이용할 수 있는 상태입니까.
	 * @param status 검증대상 유저의 상태값
	 * @return
	 */
	public static boolean isValid(UserStatus status){
		for(UserStatus validStatus : getValid()) if(status==validStatus) return true;
		return false;
	}
	public static boolean isPause(UserStatus status) {
		for(UserStatus pauseStatus : getPause())
			if(pauseStatus==status)
				return true;

		return false;
	}

	public static UserStatus[] getExpired(){
		return new UserStatus[]{ USER_STATUS_EXPIRE_RESERVED, USER_STATUS_EXPIRED };
	}
	public static boolean isExpired(UserStatus status){
		for(UserStatus expiredStatus : getExpired())
			if(status==expiredStatus) return true;
		return false;
	}
	public static boolean isExpiredArray(UserStatus[] statues){
		int count = 0;
		for(UserStatus expiredStatus : getExpired()) {
			for (UserStatus status : statues)
			{
				if (status == expiredStatus)
					count++;
			}
		}

		if (count == statues.length)
			return true;
		else
			return false;
	}

	/**
	 * 접근 가능 사용자
	 *
	 * @return
	 */
	public static UserStatus [] getUserAccessible(){
		return new UserStatus[]{ USER_STATUS_ACTIVE };
	}
	public static boolean isUserAccessible(UserStatus status){
		for(UserStatus userAccessibleStatus : getUserAccessible())
			if(userAccessibleStatus==status) return true;
		return false;
	}

	/**
	 * 탈퇴할 수 있는 상태
	 * @return int[] : 상태값들의 배열
	 * @author J.minjun
	 */
	public static final UserStatus [] getUserOutable(){
		return new UserStatus[]{ USER_STATUS_ACTIVE, USER_STATUS_PAUSE };
	}
	public static final boolean isUserOutable(UserStatus status){
		for(UserStatus userOutableStatus : getUserOutable())
			if(userOutableStatus==status) return true;
		return false;
	}

	/**
	 * 월정액을 처리할 수 있는 상태
	 * @return
	 */
	public static UserStatus [] getUserFixedable(){
		return new UserStatus[]{ USER_STATUS_ACTIVE, USER_STATUS_PAUSE };
	}
	public static boolean isUserFixedable(UserStatus status) {
		for(UserStatus userAccessibleStatus : getUserFixedable())
			if(userAccessibleStatus==status) return true;
		return false;
	}

}
