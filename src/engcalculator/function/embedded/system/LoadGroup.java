/*
 *     ICE (Interval Calculator for Engineer) is a programmable calculator working on intervals.
 *     Copyright (C) 2009  Simone Pernice
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public InfLicense as published by
 *     the Free Software Foundation, either version 3 of the InfLicense, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public InfLicense for more details.
 *
 *     You should have received a copy of the GNU General Public InfLicense
 *     along with this program.  CndIf not, see <http://www.gnu.org/licenses/>.
 */
package engcalculator.function.embedded.system;

import static engcalculator.function.Function.throwNewCalculusException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LoadGroup {

    private static final String[] ICENATIVEGROUPS = {"arithmetic", "calculus", "compare", "complex", "conditional", "constant", "convert", "decibel", "define", "diffuse", "engineer", "equation", "file", "flowControl", "fourier", "functionScan", "hyperbolic", "information", "integer", "interval", "list", "literal", "logarithm", "logic", "matrix", "measurementUnit", "multilineExpression", "optimization", "plot", "polynomial", "power", "program", "reactiveExpression", "setInterval", "setList", "statistic", "system", "trigonometry", "vector"};

    private static final String[] ICENATIVESUPERGROUPBASE = {"BASE", "arithmetic", "compare", "complex", "define", "diffuse", "information", "interval", "list", "logarithm", "power", "reactiveExpression", "system", "trigonometry"};
    private static final String[] ICENATIVESUPERGROUPPROGRAM = {"PROGRAM", "conditional", "flowControl", "literal", "logic", "multilineExpression", "program"};
    private static final String[] ICENATIVESUPERGROUPENGINEER = {"ENGINEER", "constant", "convert", "decibel", "engineer", "equation", "fourier", "optimization", "plot"};    
    private static final String[] ICENATIVESUPERGROUPALL = new String[ICENATIVEGROUPS.length + 1];
    static {
        ICENATIVESUPERGROUPALL[0] = "ALL";
        System.arraycopy(ICENATIVEGROUPS, 0, ICENATIVESUPERGROUPALL, 1, ICENATIVEGROUPS.length);
    }
    
    private static final String[][] ICENATIVESUPERGROUPS = {ICENATIVESUPERGROUPBASE, ICENATIVESUPERGROUPENGINEER, ICENATIVESUPERGROUPPROGRAM, ICENATIVESUPERGROUPALL};

    private static final String STANDARDICEGROUPPATH = "engcalculator.function.embedded.";
    private static final String LOADERCLASSNAME = ".Loader";
    private static final ArrayList<String> ALREADYLOADED = new ArrayList<String>();

    static {//this is a check ICENATIVEGROUPS is in alphabetical order otherwise the rest would not work
        String[] temp = ICENATIVEGROUPS.clone();
        Arrays.sort(temp);
        if (!Arrays.equals(temp, ICENATIVEGROUPS)) {
            throw new RuntimeException("The group array was not in alphabetical order ");
        }
    }
    
    public static void initialize () {
        ALREADYLOADED.clear();
    }
    
    public static String getGroups () {
        return Arrays.toString(ICENATIVEGROUPS);
    }
    
    public static String getSuperGroups () {
        result = new StringBuilder ();
        for (String[] sg : ICENATIVESUPERGROUPS) {
            boolean sgname = true;
            for (String g : sg) {
                if (sgname) {
                    result.append("Super Group ");
                    result.append(g);
                    result.append(" composed by groups: ");
                    sgname = false;
                } else {
                    result.append (g);
                    result.append (", ");
                }
            }
            result.setLength(result.length()-2);
            result.append("; ");
        }
        return result.toString();
    }

    public static String areGroupsLoaded(String[] groups) {
        StringBuilder res = new StringBuilder();

        for (String g : groups) {
            if (!ALREADYLOADED.contains(g)) {
                res.append(g).append(", ");
            }
        }
        if (res.length() == 0) {
            return null;
        }
        res.setCharAt(res.length() - 2, ' ');
        return res.toString();
    }
    
    private static StringBuilder result;
    private static boolean isSomeFunctionLoaded;

    private LoadGroup() {} //it is a static class should not be instantiated

    private static boolean canGroupBeLoaded(String group) {
    isSomeFunctionLoaded = true;
        if (!ALREADYLOADED.contains(group)) {            
            ALREADYLOADED.add(group);
            return true;
        } else {
            result.append(" The group ").append(group).append(" was already loaded,");
            return false;
        }
    }

    private static void addResult(boolean ndi, String group) throws Exception {
        if (ndi) {
            result.append(" Loaded ").append(group).append(" group correctly,");
        } else {
            throwNewCalculusException(" It was not possible to load some function of " + group + " group due to name collision,");
        }
    }
    
    private static LinkedList<String> resolvePathName (String group) {
        LinkedList<String> gn = new LinkedList<String>();
        result = new StringBuilder();
        isSomeFunctionLoaded = false;

        if (group.contains(".")) {//full group path, not try to guess the real path
            int i = group.lastIndexOf(".") + 1;
            gn.add (group.substring(0, i));
            gn.add (group.substring(i));

        } else if (Arrays.binarySearch(ICENATIVEGROUPS, group) < 0) {//short path not exactly matching an existing group

            //try to guess the path for a single group if it is no spell completanly 
            String g = null;
            for (String gro : ICENATIVEGROUPS) {
                if (gro.startsWith(group)) {
                    if (g == null) {
                        g = gro;
                    } else {// It was not possible to find an unique matching between a group it still may be a superGroup
                        g = null;
                        break;
                    }
                }
            }
            if (g != null) {//found group matching
                gn.add (STANDARDICEGROUPPATH);
                gn.add (g);                
            } else {//try to guess if it is a super group
                for (String[] superG : ICENATIVESUPERGROUPS) {
                    boolean firstElement;
                    firstElement = true;
                    for (String sg : superG) {
                        if (firstElement) {
                            firstElement = false;
                            if (!sg.equals(group)) {
                                break;
                            }
                            continue; //skip the super group loading
                        }
                        //If the super group is the same all its groups are loaded                        
                        gn.add (sg);
                    }
                }
                gn.addFirst(STANDARDICEGROUPPATH);
            }
        } else {//short path exactly matching an existing group
            gn.add(STANDARDICEGROUPPATH);
            gn.add(group);
        }

        return gn;
    }

    public static String loadGroup(String group) throws Exception {
        result = new StringBuilder();
        isSomeFunctionLoaded = false;
        Iterator<String> gn = resolvePathName(group).iterator();
        if (! gn.hasNext()) throwNewCalculusException("The group " + group + " does not exists.");
        String g = gn.next();
        while (gn.hasNext()) LoadGroup.loadGroup(g, gn.next());
        
        if (!isSomeFunctionLoaded) {
            throwNewCalculusException("The group " + group + " does not exists.");
        }

        result.setLength(result.length()-1);//to remove the ending comma
        return result.toString();
    }

    private static void loadGroup(String groupPath, String group) throws Exception {
        if (canGroupBeLoaded(group)) {
            GroupLoader loader = null;
            try {
                loader = (GroupLoader) Class.forName(groupPath + group + LOADERCLASSNAME).getConstructor(String.class).newInstance(group);
            } catch (Exception e) {
                System.out.println("Error loading the class: "+e.getMessage());
            }
            if (loader == null) {
                throwNewCalculusException("The loader " + groupPath + group + LOADERCLASSNAME + " for the group "+group+" was not found.");
            }
            loader.initialize();
            loader.loadGroupFunctions();
            addResult(loader.isAnyDuplicateFunction(), group);
        }
    }

    public static String getGroupListOfFunctions(String group) throws Exception {
        StringBuilder result = new StringBuilder ();
        Iterator<String> gn = resolvePathName(group).iterator();
        if (! gn.hasNext()) throwNewCalculusException("The group " + group + " does not exists.");
        String g = gn.next();
        while (gn.hasNext()) result.append(LoadGroup.getGroupListOfFunctions(g, gn.next())).append ("; ");
        
        return result.toString();
    }
    
    private static String getGroupListOfFunctions(String groupPath, String group) throws Exception {
            GroupLoader loader = null;
            try {
                loader = (GroupLoader) Class.forName(groupPath + group + LOADERCLASSNAME).getConstructor(String.class).newInstance(group);
            } catch (Exception e) {
                System.out.println("Error loading the class: "+e.getMessage());
            }
            if (loader == null) {
                throwNewCalculusException("The loader " + groupPath + group + LOADERCLASSNAME + " for the group "+group+" was not found.");
            }
            return loader.getListOfFunctions();
    }
    
}
