package edu.umd.cs.findbugs.detect;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Method;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Detector;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.visitclass.PreorderVisitor;

public class ConstantNameChecker extends PreorderVisitor implements Detector{

	private final BugReporter bugReporter;

	public ConstantNameChecker(BugReporter bugReporter) {
        this.bugReporter = bugReporter;
    }
	
	@Override
    public void visit(Field obj) {
        if (badFieldName(obj)) {
            bugReporter.reportBug(new BugInstance(this, "CDF_CONSTANT_NOT_CAPITALIZED", LOW_PRIORITY).addClass(this).addVisitedField(this));
        }
    }

    private boolean badFieldName(Field obj) {
        String fieldName = obj.getName();
        if (obj.isFinal()) {
	        for (int i = 0; i < fieldName.length(); i++){
	            char c = fieldName.charAt(i);        
	            if (Character.isLowerCase(c)) {
	            	return true;
	            }
	        }
        }
        return false;
    }

    @Override
    public void visitClassContext(ClassContext classContext) {
        classContext.getJavaClass().accept(this);
    }

    @Override
    public void report() {

    }
	
}
