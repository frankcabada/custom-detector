package edu.umd.cs.findbugs.detect;

import org.apache.bcel.classfile.Method;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Detector;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.visitclass.PreorderVisitor;

public class CustomDetector extends PreorderVisitor implements Detector {

	private final BugReporter bugReporter;

	public CustomDetector(BugReporter bugReporter) {
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
                    bugReporter.reportBug(new BugInstance(this, "CUSTOM_DETECTOR_BUG", HIGH_PRIORITY).addClassAndMethod(this));
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