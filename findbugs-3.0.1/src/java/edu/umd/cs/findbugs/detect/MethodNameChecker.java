package edu.umd.cs.findbugs.detect;

import org.apache.bcel.classfile.Method;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Detector;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.visitclass.PreorderVisitor;

import java.util.Set;
import java.util.HashSet;

public class MethodNameChecker extends PreorderVisitor implements Detector {

	private final BugReporter bugReporter;
    private Set<String> originalMethodNames;
    private Set<String> lowercaseMethodNames;

	public MethodNameChecker(BugReporter bugReporter) {
        this.bugReporter = bugReporter;
        this.originalMethodNames = new HashSet<String>();
        this.lowercaseMethodNames = new HashSet<String>();
    }

    @Override
    public void visit(Method obj) {
    	String methodName = getMethodName();
        //ignore class name read as method "<init>"
        if (!methodName.contains("<") && !methodName.contains(">")) {
            //check that there are no symbols in each method name
            for (int i =0; i<methodName.length(); i++) {
                char c = methodName.charAt(i);
                if (!Character.isLetterOrDigit(c)) {
                    bugReporter.reportBug(new BugInstance(this,
                        "CDM_METHOD_NAME_SPECIAL_CHAR",
                        LOW_PRIORITY).addClassAndMethod(this));
                    break;
                }
            }
            //method names should not be too long
            if (methodName.length() >= 70) {
                bugReporter.reportBug(new BugInstance(this,
                    "CDM_METHOD_NAME_LENGTH", LOW_PRIORITY).addClassAndMethod(this));
            }
            //skip checking this method if it is exactly the same as another method
            //Findbugs already checks this case for potential method overloading
            if (this.originalMethodNames.add(methodName)) {
                //check if only difference between method names is capitalization
                if (!this.lowercaseMethodNames.add(methodName.toLowerCase())) {
                    bugReporter.reportBug(new BugInstance(this,
                        "CDM_CONFUSING_METHOD_NAMES",
                        LOW_PRIORITY).addClassAndMethod(this));
                }
            }
        }
    }

    @Override
    public void visitClassContext(ClassContext classContext) {
        classContext.getJavaClass().accept(this);
    }

    @Override
    public void report() {
    }
}