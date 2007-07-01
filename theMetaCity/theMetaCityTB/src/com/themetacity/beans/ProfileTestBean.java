package com.themetacity.beans;

import com.themetacity.typebeans.ProfileBean;

import java.util.LinkedList;
import java.io.Serializable;

/**
 * User: doug
 * Date: Feb 11, 2007
 */
public class ProfileTestBean implements Serializable {

    public ProfileTestBean() {          // empty constructor
    }

    LinkedList<ProfileBean> listOfBeans;    // The list of profile beans

    public LinkedList getTestProfile() {

        for (int i = 0; i < 5; i++) {
            ProfileBean profile = new ProfileBean();

            profile.setPseudonym("Doug" + i);

            listOfBeans.add(profile);

        }

        return listOfBeans;
    }
}
