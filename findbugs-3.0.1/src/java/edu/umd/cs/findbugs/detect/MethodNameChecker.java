package edu.umd.cs.findbugs.detect;

import org.apache.bcel.classfile.Method;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Detector;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.visitclass.PreorderVisitor;

public class MethodNameChecker extends PreorderVisitor implements Detector {

	private final BugReporter bugReporter;

	public MethodNameChecker(BugReporter bugReporter) {
        this.bugReporter = bugReporter;
    }

    @Override
    public void visit(Method obj) {
    	String methodName = getMethodName();
        for (int i =0; i<methodName.length(); i++)
        {
            if (!methodName.contains("<init>")){
                char c = methodName.charAt(i);
                if (!Character.isLetterOrDigit(c)) {
                    bugReporter.reportBug(new BugInstance(this, "CDM_METHOD_NAME_SPECIAL_CHAR", HIGH_PRIORITY).addClassAndMethod(this));
                }
            }
            if (methodName.length() >= 70){
                bugReporter.reportBug(new BugInstance(this, "CDM_METHOD_NAME_LENGTH", HIGH_PRIORITY).addClassAndMethod(this));
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