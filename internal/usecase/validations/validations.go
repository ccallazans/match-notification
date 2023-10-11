package validations

type Rule func(key string, value interface{}) error

type Rules []Rule

type Validator struct {
	rules Rules
}

func (v *Validator) Add(rule Rule) {
	v.rules = append(v.rules, rule)
}

func (v *Validator) Validate(data map[string]interface{}) []error {
	var errors []error
	for _, rule := range v.rules {
		for key, value := range data {
			if err := rule(key, value); err != nil {
				errors = append(errors, err)
			}
		}
	}
	return errors
}
