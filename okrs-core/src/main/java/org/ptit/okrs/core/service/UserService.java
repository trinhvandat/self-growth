package org.ptit.okrs.core.service;

import org.ptit.okrs.core.constant.Gender;
import org.ptit.okrs.core.entity.User;
import org.ptit.okrs.core.model.UserResponse;
import org.ptit.okrs.core.service.base.BaseService;

public interface UserService extends BaseService<User> {

  /**
   * create new user
   *
   * @param accountId - the accountId of account is logging
   * @param name - the name of the user
   * @param email - the email of the user, is mail never create account
   * @return - information of the user after created
   */
  UserResponse create(String accountId, String name, String email);

  /**
   * update user existed
   * @param userId - The userId's user is logging
   * @param name - The name's user need update
   * @param phone - The phone's user need update
   * @param email - The email's user need update
   * @param dateOfBirth - The dateOfBirth's user need update
   * @param gender - The gender's user need update
   * @param address - The address's user need update
   * @return information of the user after updated
   */
  UserResponse update(
      String userId,
      String name,
      String phone,
      String email,
      Long dateOfBirth,
      Gender gender,
      String address);

  /**
   * get user details
   * @param userId - the userId of user is logging
   * @return information of the user after find by user id (user id get by jwt)
   */
  UserResponse get(String userId);

  /**
   * update avatar
   * @param userId - the userId of user is logging
   * @param avatar - the avatar is path save avatar from server user need update
   * @return - The URI api get avatar
   */
  String changePathAvatar(String userId, String avatar);

  /**
   * get avatar of user by userid
   * @param userId - the userId of user is logging
   * @return path avatar of user is saved in server
   */
  String getAvatar(String userId);
}
